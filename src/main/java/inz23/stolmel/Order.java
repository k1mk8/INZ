package com.example.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import org.json.*;


public class Order {

    private static final String DB_URL = "jdbc:postgresql://inz23_db_1:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static ResultSet execute(String query) {
        ResultSet resultSet = null;
        try {
            System.out.println("==== query init ====");

            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish a connection to the PostgreSQL database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("==== connection established ====");

            // Create a PreparedStatement for the SELECT query
            PreparedStatement selectStatement = connection.prepareStatement(query);

            // Execute the SELECT query and get the result set
            resultSet = selectStatement.executeQuery();
            System.out.println("==== query executed ====");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static JSONArray getOrdersOfClient(Integer clientId) {
        System.out.println("==== getOrdersOfClient init ====");

        JSONArray clientOrders = new JSONArray();
        try {
            String selectSql = String.format("""
            SELECT * FROM "order"
            WHERE '%d' = client_id AND 'Aktywny Koszyk' != state 
            """, clientId);
            ResultSet resultSet = PostgreSQL.execute(selectSql);
            while (resultSet.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", resultSet.getString("id"));
                jo.put("state", resultSet.getString("state"));
                jo.put("timestamp", resultSet.getString("timestamp"));
                clientOrders.put(jo);
            }
            System.out.println(clientOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientOrders;
    }

    public static JSONArray getProductIdsFromOrder(Integer orderId) {
        System.out.println("==== getProductsFromOrder init ====");

        JSONArray orderProducts = new JSONArray();
        try {
            String selectSql = String.format("""
            SELECT product_id FROM BASKET
            WHERE '%d' = order_id
            """, orderId);
            ResultSet resultSet = PostgreSQL.execute(selectSql);
            while (resultSet.next()) {
                JSONObject jo = new JSONObject();
                jo.put("product_id", resultSet.getString("product_id"));
                orderProducts.put(jo);
            }
            System.out.println(orderProducts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderProducts;
    }

    public static JSONObject getBasketOfClient(Integer clientId) {
        System.out.println("==== getBasketOfClient init ====");

        JSONObject clientBasket = new JSONObject();
        try {
            String selectSql = String.format("""
            SELECT * FROM "order"
            WHERE '%d' = client_id AND 'Aktywny Koszyk' = state 
            """, clientId);
            ResultSet resultSet = PostgreSQL.execute(selectSql);
            if (resultSet.next()) {
                clientBasket.put("id", resultSet.getString("id"));
                clientBasket.put("state", resultSet.getString("state"));
                clientBasket.put("timestamp", resultSet.getString("timestamp"));
            }
            System.out.println(clientBasket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientBasket;
    }

    public static void addToBasket(Integer order_id, Integer product_id) {
        System.out.println("==== addToBasket init ====");
        try {
            String selectSql = String.format("""
            INSERT INTO basket(order_id, product_id) VALUES (%d, %d)""", 
            order_id, product_id);
            ResultSet resultSet = PostgreSQL.execute(selectSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Product getProductFromId(Integer productId) {
        System.out.println("==== getProductFromId init ====");

        Product product = null;
        try {
            String selectSql = String.format("""
            SELECT * FROM PRODUCT
            WHERE '%d' = id
            """, productId);
            ResultSet resultSet = PostgreSQL.execute(selectSql);
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");
                product = new Product(id, name, price);
            }
            System.out.println(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}





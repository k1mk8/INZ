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
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  


public class Order {
    private static final Date date = Calendar.getInstance().getTime();  
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
    private static final String strDate = dateFormat.format(date);

    public static JSONArray getOrdersOfClient(Integer clientId, PostgreSQL postgreSQL) {
        System.out.println("==== getOrdersOfClient init ====");

        JSONArray clientOrders = new JSONArray();
        try {
            String selectSql = String.format("""
            SELECT * FROM "order"
            WHERE '%d' = client_id AND 'Aktywny Koszyk' != state 
            """, clientId);
            postgreSQL.execute(selectSql);
            while (postgreSQL.resultSet.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", postgreSQL.resultSet.getString("id"));
                jo.put("state", postgreSQL.resultSet.getString("state"));
                jo.put("timestamp", postgreSQL.resultSet.getString("timestamp"));
                clientOrders.put(jo);
            }
            postgreSQL.terminate();
            System.out.println(clientOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientOrders;
    }

    public static JSONObject createOrderForClient(Integer clientId, PostgreSQL postgreSQL) {
        System.out.println("==== createOrderForClient init ====");

        JSONObject clientBasket = new JSONObject();
        try {
            String selectSql = String.format("""
            SELECT id FROM "order"
            ORDER BY id DESC
            LIMIT 1
            """);
            postgreSQL.execute(selectSql);
            Integer freeID = 0;
            if (postgreSQL.resultSet.next()) {
                freeID = postgreSQL.resultSet.getInt("id") + 1;
            }
            postgreSQL.terminate();
            clientBasket.put("id", freeID);
            clientBasket.put("state", "Aktywny Koszyk");
            clientBasket.put("client_id", clientId);
            clientBasket.put("timestamp", strDate);
            System.out.println(clientBasket);
            System.out.println("==== inserting order init ====");
            String insertSql = String.format("""
            INSERT INTO "order"(id, state, client_id, timestamp) VALUES (%d, '%s', '%s', '%s')
            """, freeID, "Aktywny Koszyk", clientId, strDate);
            postgreSQL.execute(insertSql);
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientBasket;
    }

    public static JSONArray getProductIdsFromOrder(Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== getProductsFromOrder init ====");

        JSONArray orderProducts = new JSONArray();
        try {
            String selectSql = String.format("""
            SELECT product_id, amount FROM BASKET
            WHERE '%d' = order_id
            """, orderId);
            postgreSQL.execute(selectSql);
            while (postgreSQL.resultSet.next()) {
                JSONObject jo = new JSONObject();
                jo.put("product_id", postgreSQL.resultSet.getString("product_id"));
                jo.put("amount", postgreSQL.resultSet.getString("amount"));
                orderProducts.put(jo);
            }
            postgreSQL.terminate();
            System.out.println(orderProducts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderProducts;
    }

    public static JSONObject getBasketOfClient(Integer clientId, PostgreSQL postgreSQL) {
        System.out.println("==== getBasketOfClient init ====");

        JSONObject clientBasket = new JSONObject();
        try {
            String selectSql = String.format("""
            SELECT * FROM "order"
            WHERE '%d' = client_id AND 'Aktywny Koszyk' = state 
            """, clientId);
            postgreSQL.execute(selectSql);
            if (postgreSQL.resultSet.next()) {
                clientBasket.put("id", postgreSQL.resultSet.getString("id"));
                clientBasket.put("state", postgreSQL.resultSet.getString("state"));
                clientBasket.put("timestamp", postgreSQL.resultSet.getString("timestamp"));
            }
            postgreSQL.terminate();
            System.out.println(clientBasket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientBasket;
    }

    public static void addToBasket(Integer orderId, Integer productId, Integer amount, PostgreSQL postgreSQL) {
        System.out.println("==== addToBasket init ====");
        try {
            String insertSql = String.format("""
            INSERT INTO basket(order_id, product_id, amount) VALUES (%d, %d, %d)""", 
            orderId, productId, amount);
            postgreSQL.execute(insertSql);
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFromBasket(Integer orderId, Integer productId, PostgreSQL postgreSQL) {
        System.out.println("==== removeFromBasket init ====");
        try {
            String removeSql = String.format("""
            DELETE FROM basket
            WHERE order_id = %d AND product_id = %d""", 
            orderId, productId);
            postgreSQL.execute(removeSql);
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Product getProductFromId(Integer productId, PostgreSQL postgreSQL) {
        System.out.println("==== getProductFromId init ====");

        Product product = null;
        try {
            String selectSql = String.format("""
            SELECT * FROM PRODUCT
            WHERE '%d' = id
            """, productId);
            postgreSQL.execute(selectSql);
            if (postgreSQL.resultSet.next()) {
                Integer id = postgreSQL.resultSet.getInt("id");
                String name = postgreSQL.resultSet.getString("name");
                String price = postgreSQL.resultSet.getString("price");
                product = new Product(id, name, price);
            }
            postgreSQL.terminate();
            System.out.println(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}





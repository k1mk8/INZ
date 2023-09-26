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
            SELECT id, state FROM "order"
            WHERE '%d' = client_id
            """, clientId);
            ResultSet resultSet = PostgreSQL.execute(selectSql);
            while (resultSet.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", resultSet.getString("id"));
                jo.put("state", resultSet.getString("state"));
                clientOrders.put(jo);
            }
            System.out.println(clientOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientOrders;
    }
}





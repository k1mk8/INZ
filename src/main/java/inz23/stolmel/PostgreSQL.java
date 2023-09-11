package com.example.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PostgreSQL {

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

    public static void insertData(Connection connection, String name, int age) {
        try {
            // Define the SQL INSERT statement
            String sql = "INSERT INTO Client (name, age) VALUES (?, ?)";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the values for the placeholders
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);

            // Execute the INSERT statement
            preparedStatement.executeUpdate();

            // Close the PreparedStatement
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // public static String getAllClients() {
    // List<Client> ans = new ArrayList<Client>();
    // System.out.println("==== receive init ====");
    // try {
        
    //     Connection connection = this.connect();
    //     // Define the SQL SELECT statement
    //     String selectSql = "SELECT * FROM client";

    //     // Create a PreparedStatement for the SELECT query
    //     PreparedStatement selectStatement = connection.prepareStatement(selectSql);

    //     // Execute the SELECT query and get the result set
    //     ResultSet resultSet = selectStatement.executeQuery();
    //     System.out.println("==== query executed ====");

    //     // Process and display the retrieved data
    //     while (resultSet.next()) {
    //         int id = resultSet.getInt("id");
    //         String name = resultSet.getString("name");
    //         String surname = resultSet.getString("surname");
    //         String number = resultSet.getString("number");
    //         String email = resultSet.getString("email");
    //         String hash = resultSet.getString("hash");
    //         Client client = new Client(id, name, surname, number, email, hash);
    //         ans.add(client);

    //         System.out.println(client);
    //     }

    //     // Close the ResultSet, PreparedStatement, and the SELECT statement
    //     resultSet.close();
    //     selectStatement.close();
    //     connection.close();

    // } catch (Exception e) {
    //     e.printStackTrace();
    // }
    // String answer = "";
    // for(Client element : ans) {
    //     answer += " ";
    //     answer += element.getId();
    //     answer += " ";
    //     answer += element.getName();
    //     answer += " ";
    //     answer += element.getSurname();
    //     answer += " ";
    //     answer += element.getNumber();
    //     answer += " ";
    //     answer += element.getEmail();
    //     answer += " ";
    //     answer += element.getHash();
    //     answer += "\r\n";
    // }
    // return answer;
    // }

    public static Client getClientByEmail(String getEmail) {
        System.out.println("==== getCLientByEmail init ====");
        Client client = null;
        try {
            String selectSql = String.format("SELECT * FROM client WHERE email = '%s'", getEmail);
            ResultSet resultSet = PostgreSQL.execute(selectSql);

            // Process and display the retrieved data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String number = resultSet.getString("number");
                String email = resultSet.getString("email");
                String hash = resultSet.getString("hash");
                client = new Client(id, name, surname, number, email, hash);
            }

            System.out.println(client);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public static int getFreeClientId() {
        System.out.println("==== getFreeId init ====");
        int id = -1;
        try {
            String selectSql = String.format("SELECT id FROM client ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = PostgreSQL.execute(selectSql);

            // Process and display the retrieved data
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                System.out.println(String.format("%d", id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static boolean login (String getEmail, String getPassword) {
        System.out.println("==== login init ====");

        String getHash = SHA512.hash(getPassword);
        
        Client client = null;
        try {
            String selectSql = String.format("SELECT * FROM client WHERE email = '%s' AND hash = '%s'", getEmail, getHash);
            ResultSet resultSet = PostgreSQL.execute(selectSql);

            // Process and display the retrieved data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String number = resultSet.getString("number");
                String email = resultSet.getString("email");
                String hash = resultSet.getString("hash");
                client = new Client(id, name, surname, number, email, hash);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (client == null ? false : true);
    }

    public static boolean register (Client client) {
        System.out.println("==== register init ====");
        
        boolean status = false;
        try {
            String insertSql = String.format("INSERT INTO client(id, \"name\", \"surname\", \"number\", \"email\", \"hash\") VALUES (%d, '%s', '%s', '%s', '%s', '%s')", client.getId(), client.getName(), client.getSurname(), client.getNumber(), client.getEmail(), client.getHash());
            ResultSet resultSet = PostgreSQL.execute(insertSql);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}





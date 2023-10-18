package com.example.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class PostgreSQL {

    private final String DB_URL = "jdbc:postgresql://inz23_db:5432/postgres";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "postgres";
    public boolean isFree = true;
    public PreparedStatement selectStatement = null;
    public ResultSet resultSet = null;
    public Connection connection = null;

    public void execute(String query, String queryType) {
        try {
            System.out.println("==== query init ====");

            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            
            while (!this.isFree){
                System.out.println("sleep");
                Thread.sleep(100);
            }
            this.isFree = false;

            // Establish a connection to the PostgreSQL database
            this.connection = DriverManager.getConnection(this.DB_URL, this.DB_USER, this.DB_PASSWORD);
            System.out.println("==== connection established ====");

            // Create a PreparedStatement for the SELECT query
            this.selectStatement = connection.prepareStatement(query);

            // Execute the SELECT query and get the result set
            if(queryType == "select"){
                this.resultSet = this.selectStatement.executeQuery();
            }
            else {
                this.selectStatement.execute();
            }
            System.out.println("==== query executed ====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        try {
            this.resultSet.close();
            this.connection.close();
            this.isFree = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





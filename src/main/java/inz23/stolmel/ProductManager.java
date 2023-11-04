package com.example.application;

import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class ProductManager {

    public static int getProductId(String productName, PostgreSQL postgreSQL) {
        System.out.println("==== getProductId init ====");
        int id = -1;
        try {
            String selectSql = String.format("""
            SELECT id FROM PRODUCT
            WHERE name = '%s'""", productName);
            postgreSQL.execute(selectSql, "select");

            // Process and display the retrieved data
            while (postgreSQL.resultSet.next()) {
                id = postgreSQL.resultSet.getInt("id");
                System.out.println(String.format("%d", id));
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    
    public static List<JSONObject> getNeededProfessions(Integer productId, PostgreSQL postgreSQL) {
        System.out.println("==== getNeededProfession init ====");
        List<JSONObject> professionsTime = new ArrayList<JSONObject>();
        try {
            String selectSql = String.format("""
            SELECT Profession, Time_needed FROM PRODUCT_COMPONENT
            WHERE '%s' = Product_id""", productId);
            postgreSQL.execute(selectSql, "select");

            // Process and display the retrieved data
            while (postgreSQL.resultSet.next()) {
                String Profession = postgreSQL.resultSet.getString("Profession");
                int TimeNeeded = postgreSQL.resultSet.getInt("Time_needed");
                JSONObject jo = new JSONObject();
                jo.put("Profession", Profession);
                jo.put("Time_needed", TimeNeeded);
                professionsTime.add(jo);
                System.out.println(String.format("%s, %d", Profession, TimeNeeded));
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professionsTime;
    }

    public static JSONObject getProductDetails(Integer productId, PostgreSQL postgreSQL) {
        System.out.println("==== getProductDetails init ====");
        JSONObject productDetails = new JSONObject();
        try {
            String selectSql = String.format("""
            SELECT * FROM product
            WHERE '%d' = id""", productId);
            postgreSQL.execute(selectSql, "select");

            // Process and display the retrieved data
            if (postgreSQL.resultSet.next()) {
                productDetails.put("id", postgreSQL.resultSet.getInt("id"));
                productDetails.put("price", postgreSQL.resultSet.getInt("price"));
                productDetails.put("type", postgreSQL.resultSet.getString("type"));
                productDetails.put("name", postgreSQL.resultSet.getString("name"));
                productDetails.put("description", postgreSQL.resultSet.getString("description"));
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDetails;
    }

    public static boolean addProduct(String productName, String productDimension, 
      String productType, Integer productPrice, String productDescription, PostgreSQL postgreSQL) {
        System.out.println("==== addProduct init ====");
        try {
            String insertSql = String.format("""
            INSERT INTO product(id, price, "type", dimension, name, description) 
            VALUES ('%d', '%d', '%s', '%s', '%s', '%s')""", 10, productPrice, productType,
                productDimension, productName, productDescription);
            postgreSQL.execute(insertSql, "insert");
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void removeProduct(Integer productId, PostgreSQL postgreSQL) {
        System.out.println("==== removeProduct init ====");
        try {
            String deleteSql = String.format("""
            DELETE FROM product
            WHERE id = '%d'""", 
            productId);
            postgreSQL.execute(deleteSql, "delete");
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
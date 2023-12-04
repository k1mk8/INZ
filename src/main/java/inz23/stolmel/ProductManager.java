package com.example.application;

import java.util.ArrayList;
import java.util.List;
import org.json.*;
import java.util.Base64;


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
                productDetails.put("dimension", postgreSQL.resultSet.getString("dimension"));
                productDetails.put("image", Base64.getEncoder().encodeToString(Base64.getDecoder().decode(postgreSQL.resultSet.getBytes("image"))));
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDetails;
    }

    public static boolean addProduct(Integer freeId, String productName, String productDimension, 
      String productType, Integer productPrice, String productDescription, String productImage, PostgreSQL postgreSQL) {
        System.out.println("==== addProduct init ====");
        try {
            String insertSql = String.format("""
            INSERT INTO product(id, is_active, price, "type", dimension, name, description, image) 
            VALUES ('%d', '%b', '%d', '%s', '%s', '%s', '%s', '%s')""", freeId, true, productPrice, productType,
                productDimension, productName, productDescription, productImage);
            postgreSQL.execute(insertSql, "insert");
            postgreSQL.terminate();
            //TO DO this is a mock part
            Integer freeComponentId = ProductManager.getFreeProductComponentsId(postgreSQL);
            addProductComponents(freeComponentId, "stelaz", freeId, "stolarz", 5, postgreSQL);
            //
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean addProductComponents(Integer id, String componentName, Integer productId,
        String profession, Integer time_needed, PostgreSQL postgreSQL) {
        System.out.println("==== addProductComponents init ====");
        try {
            String insertSql = String.format("""
            INSERT INTO product_component(id, name, product_id, profession, time_needed) 
            VALUES ('%d', '%s', '%d', '%s', '%d')""", id, componentName, productId, profession,
                time_needed);
            postgreSQL.execute(insertSql, "insert");
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void manageProductStatus(Integer productId, boolean newStatus, PostgreSQL postgreSQL) {
        System.out.println("==== manageProductStatus init ====");
        try {
            String updateSql = String.format("""
            UPDATE product SET is_active = '%b'
            WHERE id = '%d'""", 
            newStatus, productId);
            postgreSQL.execute(updateSql, "update");
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONArray getProducts(PostgreSQL postgreSQL) {
        System.out.println("==== getProducts init ====");
        JSONArray products = new JSONArray();
        try {
            String selectSql = String.format("""
            SELECT type, name, is_active
            FROM product
            """);
            postgreSQL.execute(selectSql, "select");

            // Process and display the retrieved data
            while (postgreSQL.resultSet.next()) {
                JSONObject productDetails = new JSONObject();
                productDetails.put("type", postgreSQL.resultSet.getString("type"));
                productDetails.put("name", postgreSQL.resultSet.getString("name"));
                productDetails.put("is_active", postgreSQL.resultSet.getString("is_active"));
                products.put(productDetails);
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Integer getFreeProductId(PostgreSQL postgreSQL) {
        System.out.println("==== getFreeProductId init ====");
        JSONArray products = new JSONArray();
        Integer freeId = 0;
        try {
            String selectSql = String.format("""
            SELECT id
            FROM product
            ORDER BY id DESC
            LIMIT 1
            """);
            postgreSQL.execute(selectSql, "select");

            // Process and display the retrieved data
            if (postgreSQL.resultSet.next()) {
                freeId = postgreSQL.resultSet.getInt("id");
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return freeId + 1;
    }

    public static Integer getFreeProductComponentsId(PostgreSQL postgreSQL) {
        System.out.println("==== getFreeProductId init ====");
        JSONArray products = new JSONArray();
        Integer freeId = 0;
        try {
            String selectSql = String.format("""
            SELECT id
            FROM product_component
            ORDER BY id DESC
            LIMIT 1
            """);
            postgreSQL.execute(selectSql, "select");

            // Process and display the retrieved data
            if (postgreSQL.resultSet.next()) {
                freeId = postgreSQL.resultSet.getInt("id");
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return freeId + 1;
    }
}
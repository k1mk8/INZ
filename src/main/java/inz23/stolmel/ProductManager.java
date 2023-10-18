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
}
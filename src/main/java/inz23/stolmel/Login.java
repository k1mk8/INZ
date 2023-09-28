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


public class Login {

  public static Client getClientByEmail(String getEmail, PostgreSQL postgreSQL) {
        System.out.println("==== getCLientByEmail init ====");
        Client client = null;
        try {
            String selectSql = String.format("SELECT * FROM client WHERE email = '%s'", getEmail);
            postgreSQL.execute(selectSql);

            // Process and display the retrieved data
            while (postgreSQL.resultSet.next()) {
                int id = postgreSQL.resultSet.getInt("id");
                String name = postgreSQL.resultSet.getString("name");
                String surname = postgreSQL.resultSet.getString("surname");
                String number = postgreSQL.resultSet.getString("number");
                String email = postgreSQL.resultSet.getString("email");
                String hash = postgreSQL.resultSet.getString("hash");
                client = new Client(id, name, surname, number, email, hash);
            }
            postgreSQL.terminate();
            System.out.println(client);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public static int getFreeClientId(PostgreSQL postgreSQL) {
        System.out.println("==== getFreeClientId init ====");
        int id = -1;
        try {
            String selectSql = String.format("SELECT id FROM client ORDER BY id DESC LIMIT 1");
            postgreSQL.execute(selectSql);

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

    public static boolean login (String getEmail, String getPassword, PostgreSQL postgreSQL) {
        System.out.println("==== login init ====");

        String getHash = SHA512.hash(getPassword);
        
        Client client = null;
        try {
            String selectSql = String.format("SELECT * FROM client WHERE email = '%s' AND hash = '%s'", getEmail, getHash);
            postgreSQL.execute(selectSql);

            // Process and display the retrieved data
            while (postgreSQL.resultSet.next()) {
                int id = postgreSQL.resultSet.getInt("id");
                String name = postgreSQL.resultSet.getString("name");
                String surname = postgreSQL.resultSet.getString("surname");
                String number = postgreSQL.resultSet.getString("number");
                String email = postgreSQL.resultSet.getString("email");
                String hash = postgreSQL.resultSet.getString("hash");
                client = new Client(id, name, surname, number, email, hash);
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (client == null ? false : true);
    }

    public static boolean register (Client client, PostgreSQL postgreSQL) {
        System.out.println("==== register init ====");
        
        if (getClientByEmail(client.getEmail(), postgreSQL) != null)
            return false;
        try {
            String insertSql = 
                String.format("""
                INSERT INTO client(id, \"name\", \"surname\", \"number\", \"email\", \"hash\") 
                VALUES (%d, '%s', '%s', '%s', '%s', '%s')""", 
                client.getId(), client.getName(), client.getSurname(), 
                client.getNumber(), client.getEmail(), client.getHash());
            postgreSQL.execute(insertSql);
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (getClientByEmail(client.getEmail(), postgreSQL) != null ? true : false );
    }
    
    //################################################################################################

    public static boolean checkMaterialsAvailability(int productId, PostgreSQL postgreSQL) {
        System.out.println("==== checkMaterialsAvaiability init ====");
        try {
            String selectSql = String.format("""
            SELECT id, amount FROM GOODS
            WHERE '%s' = product_id AND '1' = is_available
            """, productId);
            postgreSQL.execute(selectSql);
            if (postgreSQL.resultSet.next()) {
                postgreSQL.terminate();
                return true;
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getProductId(String productName, PostgreSQL postgreSQL) {
        System.out.println("==== getProductId init ====");
        int id = -1;
        try {
            String selectSql = String.format("""
            SELECT id FROM PRODUCT
            WHERE name = '%s'""", productName);
            postgreSQL.execute(selectSql);

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
            postgreSQL.execute(selectSql);

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

    public static List<JSONObject> getLastHourOfTasks(List<JSONObject> professionsTime, PostgreSQL postgreSQL) {
        System.out.println("==== getLastHourOfTasks init ====");
        List<String> lastTimeOfSchedule = new ArrayList<String>();
        for (int i = 0; i < 6; i++)
            lastTimeOfSchedule.add("01-01-1999 00:00");
        List<Integer> referenceOfProfessions = new ArrayList<Integer>();
        referenceOfProfessions.add(5);
        referenceOfProfessions.add(5);
        referenceOfProfessions.add(1);
        referenceOfProfessions.add(2);
        referenceOfProfessions.add(3);
        referenceOfProfessions.add(4);
        Integer idx = 0;
        List<JSONObject> hoursForTasks = new ArrayList<JSONObject>();
        try {
            for (int i = 0; i < professionsTime.size(); i++) {
                // drivers cannot finish their work until it is done
                // (when they drive they cannot come back with the cargo)
                // TODO !!!!!!!!!!! pracownicy mogą być w różnej kolejności - sprawdzić czy nie trzeba sortować po zawodzie
                // json powinien zalatwic sprawe zamiast reference of professions
                String profession = professionsTime.get(i).get("Profession").toString();
                Integer time = Integer.valueOf(professionsTime.get(i).get("Time_needed").toString());
                System.out.println(professionsTime.get(i));
                System.out.println(!profession.equals("kierowca"));
                if(!profession.equals("kierowca")) {
                    String selectSql = String.format("""
                    SELECT DISTINCT ON (datetime) datetime, EMPLOYEE.id FROM SCHEDULE
                    INNER JOIN EMPLOYEE ON Employee_id = EMPLOYEE.id
                    WHERE profession = '%s' AND Is_occupied = '0'
                    AND datetime > '%s'
                    GROUP BY EMPLOYEE.id, datetime
                    ORDER BY datetime LIMIT '%s'""", 
                    profession, lastTimeOfSchedule.get(referenceOfProfessions.get(idx)), time);
                    postgreSQL.execute(selectSql);
                    while (postgreSQL.resultSet.next()) {
                        JSONObject jo = new JSONObject();
                        jo.put("timestamp", postgreSQL.resultSet.getString("datetime"));
                        jo.put("employeeId", postgreSQL.resultSet.getString("id"));
                        hoursForTasks.add(jo);
                        System.out.println(hoursForTasks);
                    }
                    postgreSQL.terminate();
                }
                else {
                    // If the delivery is longer than the single shift it can be longer than a day
                    String selectSql = String.format("""
                    SELECT DATE(a.datetime) AS date, a.id FROM
                    (
                            SELECT DISTINCT ON (datetime) datetime, EMPLOYEE.id FROM SCHEDULE
                            INNER JOIN EMPLOYEE ON Employee_id = EMPLOYEE.id
                            WHERE profession = '%s' AND Is_occupied = '0'
                            AND datetime > '%s'
                            GROUP BY EMPLOYEE.id, datetime
                            ORDER BY datetime
                    ) a
                    GROUP BY date, a.id
                    HAVING COUNT(a.datetime) >= '%s'
                    ORDER BY date
                    """, profession, lastTimeOfSchedule.get(referenceOfProfessions.get(idx)), time);
                    postgreSQL.execute(selectSql);
                    String date = "";
                    int id = -1;
                    if (postgreSQL.resultSet.next()) {
                        date = postgreSQL.resultSet.getString("date");
                        id = postgreSQL.resultSet.getInt("id");
                        System.out.println(String.format("%s, %d", date, id));
                    }
                    postgreSQL.terminate();
                    selectSql = String.format(
                    """
                            SELECT DISTINCT ON (datetime) datetime, EMPLOYEE.id FROM SCHEDULE
                            INNER JOIN EMPLOYEE ON Employee_id = EMPLOYEE.id
                            WHERE employee.id = '%s' AND Is_occupied = '0'
                            AND DATE(datetime) = '%s'
                            GROUP BY EMPLOYEE.id, datetime
                            ORDER BY datetime LIMIT '%s'
                    """, id, date, time);
                    postgreSQL.execute(selectSql);
                    while (postgreSQL.resultSet.next()) {
                        JSONObject jo = new JSONObject();
                        jo.put("timestamp", postgreSQL.resultSet.getString("datetime"));
                        jo.put("employeeId", postgreSQL.resultSet.getString("id"));
                        hoursForTasks.add(jo);
                        System.out.println(hoursForTasks);
                    }
                    postgreSQL.terminate();
                }
                lastTimeOfSchedule.set(idx, hoursForTasks.get(hoursForTasks.size() - 1).get("timestamp").toString());
                idx += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hoursForTasks;
    }

    public static void setHoursForEmployees(List<JSONObject> professionsTime, PostgreSQL postgreSQL) {
        System.out.println("==== setHoursForEmployees init ====");
        try {
            for(Integer i = 0; i < professionsTime.size(); i++) {
                //single task
                String selectSql = String.format("""
                UPDATE SCHEDULE
                SET Is_occupied = 1
                WHERE datetime = '%s' AND employee_id = '%s'
                """, professionsTime.get(i).get("timestamp"), professionsTime.get(i).get("employeeId"));
                postgreSQL.execute(selectSql);
                postgreSQL.terminate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
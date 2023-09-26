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
        System.out.println("==== getFreeClientId init ====");
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
        
        if (getClientByEmail(client.getEmail()) != null)
            return false;
        try {
            String insertSql = 
                String.format("""
                INSERT INTO client(id, \"name\", \"surname\", \"number\", \"email\", \"hash\") 
                VALUES (%d, '%s', '%s', '%s', '%s', '%s')""", 
                client.getId(), client.getName(), client.getSurname(), 
                client.getNumber(), client.getEmail(), client.getHash());
            ResultSet resultSet = PostgreSQL.execute(insertSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (getClientByEmail(client.getEmail()) != null ? true : false );
    }
    
    //################################################################################################

    public static boolean checkMaterialsAvailability(int productId) {
        System.out.println("==== checkMaterialsAvaiability init ====");
        boolean isAvailable = false;
        try {
            String selectSql = String.format("""
            SELECT id, amount FROM GOODS
            WHERE '%s' = product_id AND '1' = is_available
            """, productId);
            ResultSet resultSet = PostgreSQL.execute(selectSql);
            if (resultSet.next()) {
                isAvailable = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAvailable;
    }

    public static int getProductId(String productName) {
        System.out.println("==== getProductId init ====");
        int id = -1;
        try {
            String selectSql = String.format("""
            SELECT id FROM PRODUCT
            WHERE name = '%s'""", productName);
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
    
    public static List<JSONObject> getNeededProfessions(Integer productId) {
        System.out.println("==== getNeededProfession init ====");
        List<JSONObject> professionsTime = new ArrayList<JSONObject>();
        try {
            String selectSql = String.format("""
            SELECT Profession, Time_needed FROM PRODUCT_COMPONENT
            WHERE '%s' = Product_id""", productId);
            ResultSet resultSet = PostgreSQL.execute(selectSql);

            // Process and display the retrieved data
            while (resultSet.next()) {
                String Profession = resultSet.getString("Profession");
                int TimeNeeded = resultSet.getInt("Time_needed");
                JSONObject jo = new JSONObject();
                jo.put("Profession", Profession);
                jo.put("Time_needed", TimeNeeded);
                professionsTime.add(jo);
                System.out.println(String.format("%s, %d", Profession, TimeNeeded));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professionsTime;
    }

    public static List<JSONObject> getLastHourOfTasks(List<JSONObject> professionsTime) {
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
                    ResultSet resultSet = PostgreSQL.execute(selectSql);
                    while (resultSet.next()) {
                        JSONObject jo = new JSONObject();
                        jo.put("timestamp", resultSet.getString("datetime"));
                        jo.put("employeeId", resultSet.getString("id"));
                        hoursForTasks.add(jo);
                        System.out.println(hoursForTasks);
                    }
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
                    ResultSet resultSet = PostgreSQL.execute(selectSql);
                    String date = "";
                    int id = -1;
                    if (resultSet.next()) {
                        date = resultSet.getString("date");
                        id = resultSet.getInt("id");
                        System.out.println(String.format("%s, %d", date, id));
                    }
                    selectSql = String.format(
                    """
                            SELECT DISTINCT ON (datetime) datetime, EMPLOYEE.id FROM SCHEDULE
                            INNER JOIN EMPLOYEE ON Employee_id = EMPLOYEE.id
                            WHERE employee.id = '%s' AND Is_occupied = '0'
                            AND DATE(datetime) = '%s'
                            GROUP BY EMPLOYEE.id, datetime
                            ORDER BY datetime LIMIT '%s'
                    """, id, date, time);
                    resultSet = PostgreSQL.execute(selectSql);
                    while (resultSet.next()) {
                        JSONObject jo = new JSONObject();
                        jo.put("timestamp", resultSet.getString("datetime"));
                        jo.put("employeeId", resultSet.getString("id"));
                        hoursForTasks.add(jo);
                        System.out.println(hoursForTasks);
                    }
                }
                lastTimeOfSchedule.set(idx, hoursForTasks.get(hoursForTasks.size() - 1).get("timestamp").toString());
                idx += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hoursForTasks;
    }

    public static void setHoursForEmployees(List<JSONObject> professionsTime) {
        System.out.println("==== setHoursForEmployees init ====");
        try {
            for(Integer i = 0; i < professionsTime.size(); i++) {
                //single task
                String selectSql = String.format("""
                UPDATE SCHEDULE
                SET Is_occupied = 1
                WHERE datetime = '%s' AND employee_id = '%s'
                """, professionsTime.get(i).get("timestamp"), professionsTime.get(i).get("employeeId"));
                ResultSet resultSet = PostgreSQL.execute(selectSql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





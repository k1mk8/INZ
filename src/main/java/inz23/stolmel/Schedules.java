package com.example.application;

import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class Schedules {

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
                    WHERE profession = '%s' AND order_id is null
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
                            WHERE profession = '%s' AND order_id is null
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
                            WHERE employee.id = '%s' AND order_id is null
                            AND DATE(datetime) = '%s' AND datetime > '%s'
                            GROUP BY EMPLOYEE.id, datetime
                            ORDER BY datetime LIMIT '%s'
                    """, id, date, lastTimeOfSchedule.get(referenceOfProfessions.get(idx)), time);
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

    public static void setHoursForEmployees(List<JSONObject> professionsTime, Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== setHoursForEmployees init ====");
        try {
            for(Integer i = 0; i < professionsTime.size(); i++) {
                //single task
                String selectSql = String.format("""
                UPDATE SCHEDULE
                SET order_id = '%d'
                WHERE datetime = '%s' AND employee_id = '%s'
                """, orderId, professionsTime.get(i).get("timestamp"), professionsTime.get(i).get("employeeId"));
                postgreSQL.execute(selectSql);
                postgreSQL.terminate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSchedule(Integer productId, Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== setSchedule init ====");
        List<JSONObject> neededProfessionsTime = ProductManager.getNeededProfessions(productId, postgreSQL);
        List<JSONObject> ListOfTimestampsAndEmployees = Schedules.getLastHourOfTasks(neededProfessionsTime, postgreSQL);
        Schedules.setHoursForEmployees(ListOfTimestampsAndEmployees, orderId, postgreSQL);
        System.out.println(String.format("==== order successful ===="));
    }

    public static void removeSchedule(Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== removeSchedule init ====");
        try {
            String selectSql = String.format("""
                UPDATE SCHEDULE
                SET order_id = null
                WHERE order_id = '%d'
                """, orderId);
            postgreSQL.execute(selectSql);
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateSchedule(Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== updateSchedule init ====");
        try {            
            JSONArray orderProductsInBucket = Order.getProductIdsFromOrder(orderId, postgreSQL);
            for(int it = 0; it < orderProductsInBucket.length(); it++) {
                System.out.println(orderProductsInBucket.getJSONObject(it));
                Integer product_id = orderProductsInBucket.getJSONObject(it).getInt("product_id");
                Schedules.setSchedule(product_id, orderId, postgreSQL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package inz23.stolmel.order;
import inz23.stolmel.postgreSQL.*;
import inz23.stolmel.dataTypeClasses.*;
import inz23.stolmel.schedule.*;

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
            postgreSQL.execute(selectSql, "select");
            while (postgreSQL.resultSet.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", postgreSQL.resultSet.getInt("id"));
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

    public static JSONObject createOrderForClient(Integer clientId, Integer freeId, PostgreSQL postgreSQL) {
        System.out.println("==== createOrderForClient init ====");

        JSONObject clientBasket = new JSONObject();
        try {
            clientBasket.put("id", freeId);
            clientBasket.put("state", "Aktywny Koszyk");
            clientBasket.put("client_id", clientId);
            clientBasket.put("timestamp", strDate);
            System.out.println(clientBasket);
            System.out.println("==== inserting order init ====");
            String insertSql = String.format("""
            INSERT INTO "order"(id, state, client_id, timestamp) VALUES (%d, '%s', '%s', '%s')
            """, freeId, "Aktywny Koszyk", clientId, strDate);
            postgreSQL.execute(insertSql, "insert");
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientBasket;
    }

    public static Integer getFreeOrderId(PostgreSQL postgreSQL) {
        System.out.println("==== getFreeOrderId init ====");
        Integer freeId = -1;
        try {
            String selectSql = String.format("""
            SELECT id FROM "order"
            ORDER BY id DESC
            LIMIT 1
            """);
            postgreSQL.execute(selectSql, "select");
            if (postgreSQL.resultSet.next()) {
                freeId = postgreSQL.resultSet.getInt("id") + 1;
            }
            postgreSQL.terminate();
            System.out.println(freeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return freeId;
    }

    public static JSONArray getProductIdsFromOrder(Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== getProductsFromOrder init ====");

        JSONArray orderProducts = new JSONArray();
        try {
            String selectSql = String.format("""
            SELECT product_id, amount FROM BASKET
            WHERE '%d' = order_id
            """, orderId);
            postgreSQL.execute(selectSql, "select");
            while (postgreSQL.resultSet.next()) {
                JSONObject jo = new JSONObject();
                jo.put("product_id", postgreSQL.resultSet.getInt("product_id"));
                jo.put("amount", postgreSQL.resultSet.getInt("amount"));
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
            postgreSQL.execute(selectSql, "select");
            if (postgreSQL.resultSet.next()) {
                clientBasket.put("id", postgreSQL.resultSet.getInt("id"));
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
            postgreSQL.execute(insertSql, "insert");
            postgreSQL.terminate();
            Schedules.setSchedule(productId, orderId, postgreSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFromBasket(Integer orderId, Integer productId, PostgreSQL postgreSQL) {
        System.out.println("==== removeFromBasket init ====");
        try {
            String deleteSql = String.format("""
            DELETE FROM basket
            WHERE order_id = %d AND product_id = %d""", 
            orderId, productId);
            postgreSQL.execute(deleteSql, "delete");
            postgreSQL.terminate();
            Schedules.removeSchedule(orderId, postgreSQL);
            Schedules.updateSchedule(orderId, postgreSQL);
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
            postgreSQL.execute(selectSql, "select");
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

    public static void order(Integer productId, Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== order init ====");
        Schedules.setSchedule(productId, orderId, postgreSQL);
    }

    public static void changeOrderStatus(Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== changeOrderStatus init ====");
        try {
            String updateSql = String.format("""
            UPDATE "order" SET state = '%s'
            WHERE id = '%d'
            """, "W trakcie realizacji", orderId);
            postgreSQL.execute(updateSql, "update");
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String basketFinish(Integer orderId, PostgreSQL postgreSQL) {
        System.out.println("==== basketFinish init ====");
        String basketFinish = "";
        try {
            String selectSql = String.format("""
            SELECT datetime FROM schedule
            WHERE order_id = '%d'
            ORDER BY datetime DESC
            LIMIT 1
            """, orderId);
            postgreSQL.execute(selectSql, "select");
            if (postgreSQL.resultSet.next()) {
                basketFinish = postgreSQL.resultSet.getString("datetime");
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basketFinish;
    }
}





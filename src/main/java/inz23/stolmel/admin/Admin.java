package inz23.stolmel.admin;

import inz23.stolmel.postgreSQL.PostgreSQL;
import org.json.JSONArray;
import org.json.JSONObject;

public class Admin {

    /** Zwraca wszystkie wpisy z tabeli news jako JSONArray */
    public static JSONArray getAllNews(PostgreSQL db) {
        JSONArray arr = new JSONArray();
        try {
            String sql = "SELECT id, title, content, is_published, published_at FROM news ORDER BY published_at DESC";
            db.execute(sql, "select");
            while (db.resultSet.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", db.resultSet.getInt("id"));
                obj.put("title", db.resultSet.getString("title"));
                obj.put("content", db.resultSet.getString("content"));
                obj.put("is_published", db.resultSet.getBoolean("is_published"));
                String pub = db.resultSet.getString("published_at");
                obj.put("published_at", pub != null ? pub : JSONObject.NULL);
                arr.put(obj);
            }
            db.terminate();
        } catch (Exception e) { e.printStackTrace(); }
        return arr;
    }

    /** Dodaje nową aktualność; zwraca wygenerowane ID lub -1 */
    public static int createNews(PostgreSQL db, JSONObject input) {
        try {
            String title = input.getString("title").replace("'", "''");
            String content = input.getString("content").replace("'", "''");
            boolean isPub = input.getBoolean("is_published");
            Object pubAt = input.opt("published_at"); // ISO string lub NULL
            String sql = String.format(
                "INSERT INTO news (title, content, is_published, published_at) VALUES ('%s', '%s', %s, %s) RETURNING id",
                title, content,
                isPub ? "true" : "false",
                (pubAt == null || pubAt.equals(JSONObject.NULL)) ? "NULL" : "'" + pubAt + "'"
            );
            db.execute(sql, "select");
            if (db.resultSet.next()) {
                int id = db.resultSet.getInt("id");
                db.terminate();
                return id;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    /** Aktualizuje istniejącą aktualność według ID; zwraca true jeśli OK */
    public static boolean updateNews(PostgreSQL db, int id, JSONObject input) {
        try {
            String title = input.getString("title").replace("'", "''");
            String content = input.getString("content").replace("'", "''");
            boolean isPub = input.getBoolean("is_published");
            Object pubAt = input.opt("published_at");
            String setPublishedAt = (pubAt == null || pubAt.equals(JSONObject.NULL))
                ? "published_at = NULL"
                : "published_at = '" + pubAt + "'";
            String sql = String.format(
                "UPDATE news SET title = '%s', content = '%s', is_published = %s, %s WHERE id = %d",
                title, content,
                isPub ? "true" : "false",
                setPublishedAt,
                id
            );
            db.execute(sql, "update");
            db.terminate();
            return true;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    /** Usuwa aktualność po ID; zwraca true jeśli OK */
    public static boolean deleteNews(PostgreSQL db, int id) {
        try {
            String sql = String.format("DELETE FROM news WHERE id = %d", id);
            db.execute(sql, "update");
            db.terminate();
            return true;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}

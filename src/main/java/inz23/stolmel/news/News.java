package inz23.stolmel.news;

import inz23.stolmel.postgreSQL.*;

import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class News {
    public static JSONArray getNews(PostgreSQL postgreSQL) {
        System.out.println("==== getNews init ====");
        JSONArray newsArray = new JSONArray();

        try {
            String selectSql = """
                SELECT id, title, content, is_published, published_at
                FROM news
            """;
            postgreSQL.execute(selectSql, "select");

            while (postgreSQL.resultSet.next()) {
                JSONObject newsItem = new JSONObject();
                newsItem.put("id", postgreSQL.resultSet.getInt("id"));
                newsItem.put("title", postgreSQL.resultSet.getString("title"));
                newsItem.put("content", postgreSQL.resultSet.getString("content"));
                newsItem.put("is_published", postgreSQL.resultSet.getBoolean("is_published"));

                String publishedAt = postgreSQL.resultSet.getString("published_at");
                newsItem.put("published_at", publishedAt != null ? publishedAt : JSONObject.NULL);

                newsArray.put(newsItem);
            }

            postgreSQL.terminate();
            System.out.println("==== getNews done, count=" + newsArray.length() + " ====");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsArray;
    }
}

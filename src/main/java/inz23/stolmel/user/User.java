package inz23.stolmel.user;

import inz23.stolmel.dataTypeClasses.*;
import inz23.stolmel.postgreSQL.*;
import inz23.stolmel.sha.*;

public class User {

    public static boolean login(String email, String password, PostgreSQL postgreSQL) {
        System.out.println("==== login init ====");

        boolean success = false;
        String hash = SHA512.hash(password);

        try {
            String sql = String.format(
                "SELECT * FROM administrators WHERE email = '%s' AND password_hash = '%s'",
                email, hash
            );
            postgreSQL.execute(sql, "select");           
            while (postgreSQL.resultSet.next()) {
                System.out.println("==== connection done: " + postgreSQL.resultSet.getString("username") + " ====");
                success = true;
                break;
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==== login done: " + success + " ====");
        return success;
    }
}
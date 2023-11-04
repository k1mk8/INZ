package com.example.application;

public class User {

  public static Client getClientByEmail(String getEmail, PostgreSQL postgreSQL) {
        System.out.println("==== getCLientByEmail init ====");
        Client client = null;
        try {
            String selectSql = String.format("SELECT * FROM client WHERE email = '%s'", getEmail);
            postgreSQL.execute(selectSql, "select");

            // Process and display the retrieved data
            while (postgreSQL.resultSet.next()) {
                int id = postgreSQL.resultSet.getInt("id");
                String name = postgreSQL.resultSet.getString("name");
                String surname = postgreSQL.resultSet.getString("surname");
                String number = postgreSQL.resultSet.getString("number");
                String email = postgreSQL.resultSet.getString("email");
                String hash = postgreSQL.resultSet.getString("hash");
                boolean isAdmin = postgreSQL.resultSet.getBoolean("is_admin");
                client = new Client(id, name, surname, number, email, hash, isAdmin);
            }
            postgreSQL.terminate();
            System.out.println(client);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public static boolean login (String getEmail, String getPassword, PostgreSQL postgreSQL) {
        System.out.println("==== login init ====");

        String getHash = SHA512.hash(getPassword);
        
        Client client = null;
        try {
            String selectSql = String.format("SELECT * FROM client WHERE email = '%s' AND hash = '%s'", getEmail, getHash);
            postgreSQL.execute(selectSql, "select");

            // Process and display the retrieved data
            while (postgreSQL.resultSet.next()) {
                int id = postgreSQL.resultSet.getInt("id");
                String name = postgreSQL.resultSet.getString("name");
                String surname = postgreSQL.resultSet.getString("surname");
                String number = postgreSQL.resultSet.getString("number");
                String email = postgreSQL.resultSet.getString("email");
                String hash = postgreSQL.resultSet.getString("hash");
                boolean isAdmin = postgreSQL.resultSet.getBoolean("is_admin");
                client = new Client(id, name, surname, number, email, hash, isAdmin);
            }
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (client == null ? false : true);
    }

    public static int getFreeClientId(PostgreSQL postgreSQL) {
        System.out.println("==== getFreeClientId init ====");
        int id = -1;
        try {
            String selectSql = String.format("SELECT id FROM client ORDER BY id DESC LIMIT 1");
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
        return id + 1;
    }

    public static boolean register (Client client, PostgreSQL postgreSQL) {
        System.out.println("==== register init ====");
        
        if (getClientByEmail(client.getEmail(), postgreSQL) != null)
            return false;
        try {
            String insertSql = String.format("""
                INSERT INTO client(id, 'name', 'surname', 'number', 'email', 'hash', 'is_admin') 
                VALUES (%d, '%s', '%s', '%s', '%s', '%s', 'false')""", 
                client.getId(), client.getName(), client.getSurname(), 
                client.getNumber(), client.getEmail(), client.getHash());
            postgreSQL.execute(insertSql, "insert");
            postgreSQL.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (getClientByEmail(client.getEmail(), postgreSQL) != null ? true : false );
    }

}
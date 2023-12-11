package inz23.stolmel.postgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class PostgreSQL {

    private final String DB_URL = "jdbc:postgresql://inz23_db:5432/postgres";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "postgres";
    private ReentrantLock lock = new ReentrantLock();
    public PreparedStatement selectStatement = null;
    public ResultSet resultSet = null;
    public Connection connection = null;

    public  PostgreSQL() {
    }

    public void execute(String query, String queryType) {
        lock.lock();
        try {
            System.out.println("==== query init ====");

            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish a connection to the PostgreSQL database
            this.connection = DriverManager.getConnection(this.DB_URL, this.DB_USER, this.DB_PASSWORD);
            System.out.println("==== connection established ====");

            // Create a PreparedStatement for the SELECT query
            this.selectStatement = connection.prepareStatement(query);

            // Execute the SELECT query and get the result set
            if(queryType == "select"){
                this.resultSet = this.selectStatement.executeQuery();
            }
            else {
                this.selectStatement.execute();
            }
            System.out.println("==== query executed ====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        try {
            if(this.resultSet.isBeforeFirst()){
                this.resultSet.close();
            }
            this.connection.close();
            lock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





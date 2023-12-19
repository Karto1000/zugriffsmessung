package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connection;
    private static final String url = "jdbc:mysql://localhost:3307/m323Kontakte";
    private static final String username = "root";
    private static final String password = "root";


    public static Connection getInstance() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }

        return connection;
    }
}

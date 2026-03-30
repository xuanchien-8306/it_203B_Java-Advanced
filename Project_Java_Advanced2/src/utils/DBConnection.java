package utils;

import java.sql.Connection;
import java.sql.DriverManager;

// Class dùng để kết nối database
public class DBConnection {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/restaurant_management";
    private static final String USER = "root";
    private static final String PASS = "080306@";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

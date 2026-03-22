package Session_11.BTTH.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Quản lý kết nối DB (Singleton style đơn giản)
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/my_db1";
    private static final String USER = "root";
    private static final String PASS = "080306@";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

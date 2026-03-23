package Session_12.BTTH.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private final static String Driver = "com.mysql.cj.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/my_db1";
    private final static String USER = "root";
    private final static String PASS = "080306@";

    public static Connection openConnection() {
        Connection conn = null;

        // khai báo cho clas biết driver
        try {
            Class.forName(Driver);
            // Mở kết nối
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}

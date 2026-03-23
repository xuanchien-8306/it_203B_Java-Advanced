package Session_12.Bai_04;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/my_db1";
    private static final String USER = "root";
    private static final String PASSWORD = "080306@";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

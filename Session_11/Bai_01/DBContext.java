package Session_11.Bai_01;
import java.sql.*;

public class DBContext {

    // Hằng số cấu hình
    private static final String URL = "jdbc:mysql://localhost:3306/my_db1";
    private static final String USER = "root";
    private static final String PASSWORD = "080306@";

    // Lấy connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // SELECT
    public static void executeQuery(String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng theo thứ tự ngược
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // INSERT / UPDATE / DELETE
    public static void executeUpdate(String query) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            int rows = stmt.executeUpdate(query);

            System.out.println("Số dòng bị ảnh hưởng: " + rows);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
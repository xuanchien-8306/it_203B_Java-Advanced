package Session_11.Bai_04;

import Session_11.Bai_01.DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bai4 {
    public static void findPatientByName(String input) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Kiểm tra và loại bỏ ký tự nguy hiểm
            if (input != null) {
                input = input.replace("'", "");    // bỏ dấu '
                input = input.replace("--", "");   // bỏ comment SQL
                input = input.replace(";", "");    // bỏ kết thúc lệnh
            }

            // 2. Tạo câu SQL an toàn hơn (đã lọc)
            String sql = "SELECT * FROM patient WHERE name = '" + input + "'";

            // 3. Mở kết nối
            conn = DBContext.getConnection();

            // 4. Tạo Statement
            stmt = conn.createStatement();

            // 5. Thực thi query
            rs = stmt.executeQuery(sql);

            // 6. Duyệt kết quả
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7. Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Giả sử code hiện tại:
//    String sql = "SELECT * FROM patient WHERE name = '" + input + "'";

// Người dùng nhập:
//    input = "' OR '1'='1";

// Sau khi nối chuỗi, câu SQL trở thành:
//    SELECT * FROM patient WHERE name = '' OR '1'='1'

// Phân tích:
// '1'='1' luôn đúng (true)
// → toàn bộ điều kiện WHERE trở thành true
// → DB trả về TẤT CẢ bệnh nhân

// Ngoài ra:
// input = "'; DROP TABLE patient; --"
// → có thể phá hoại dữ liệu

// Kết luận:
// Việc nối chuỗi trực tiếp tạo lỗ hổng SQL Injection
// → cho phép bypass điều kiện hoặc phá DB
}

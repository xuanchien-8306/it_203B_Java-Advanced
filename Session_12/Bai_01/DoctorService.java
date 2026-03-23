package Session_12.Bai_01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorService {

    /*
     PHÂN TÍCH:
     - Không dùng PreparedStatement → dễ bị SQL Injection
     - Ví dụ: password = ' OR '1'='1 → login thành công sai

     - PreparedStatement:
       + SQL được biên dịch trước (pre-compiled)
       + Dữ liệu truyền vào chỉ là value, không phải lệnh SQL
       → không thể phá cấu trúc query
    */

    public static void login(String code, String pass) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // lấy connection từ DBContext
            conn = DBContext.getConnection();

            // dùng ? để tránh injection
            String sql = "SELECT * FROM doctors WHERE doctor_code = ? AND password = ?";
            ps = conn.prepareStatement(sql);

            // gán giá trị
            ps.setString(1, code);
            ps.setString(2, pass);

            // chạy query
            rs = ps.executeQuery();

            // kiểm tra kết quả
            if (rs.next()) {
                System.out.println("Đăng nhập thành công");
            } else {
                System.out.println("Sai thông tin");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
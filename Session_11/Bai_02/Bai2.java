package Session_11.Bai_02;

import Session_11.Bai_01.DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bai2 {
    //  Dùng if(rs.next()) chỉ kiểm tra 1 lần
// → chỉ lấy được bản ghi đầu tiên

//  ResultSet hoạt động như con trỏ (cursor):
// - Ban đầu: đứng TRƯỚC dòng đầu tiên
// - Mỗi lần gọi rs.next():
//      → con trỏ di chuyển xuống 1 dòng
//      → trả về true nếu còn dữ liệu
//      → false nếu đã hết

//  Nếu dùng if:
// → chỉ chạy 1 lần → không duyệt hết danh sách

//  Nếu bảng rỗng:
// → rs.next() = false → không vào if → dễ gây lỗi logic

//  Phải dùng while(rs.next()):
// → duyệt toàn bộ các dòng trong bảng

    public static void printPharmacyCatalogue() {
        Connection conn;
        conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Mở kết nối đến database
            conn = DBContext.getConnection();

            // 2. Tạo Statement để chạy SQL
            stmt = conn.createStatement();

            // 3. Câu lệnh lấy danh sách thuốc
            String sql = "SELECT name, quantity FROM medicine";

            // 4. Thực thi query → trả về ResultSet
            rs = stmt.executeQuery(sql);

            // 5. Duyệt từng dòng dữ liệu (QUAN TRỌNG)
            while (rs.next()) {
                // rs.next():
                // → di chuyển con trỏ xuống dòng tiếp theo

                // 6. Lấy dữ liệu từng cột
                String name = rs.getString("name");      // lấy tên thuốc
                int quantity = rs.getInt("quantity");    // lấy số lượng

                // 7. In ra màn hình
                System.out.println("Tên thuốc: " + name + " | Số lượng: " + quantity);
            }

            //  Nếu không có dòng nào → while không chạy → không in gì (đúng logic)

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8. Đóng tài nguyên (tránh leak)
            try {
                if (rs != null) rs.close();       // đóng ResultSet
                if (stmt != null) stmt.close();   // đóng Statement
                if (conn != null) conn.close();   // đóng Connection
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

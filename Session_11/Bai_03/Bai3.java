package Session_11.Bai_03;

import Session_11.Bai_01.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Bai3 {
    public static void updateBedStatus(String bedId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 1. Mở kết nối
            conn = DBContext.getConnection();

            // 2. Dùng PreparedStatement để tránh SQL Injection
            String sql = "UPDATE bed SET bed_status = 'Đang sử dụng' WHERE bed_id = ?";

            pstmt = conn.prepareStatement(sql);

            // 3. Gán giá trị cho dấu ?
            pstmt.setString(1, bedId);

            // 4. Thực thi update
            int rowsAffected = pstmt.executeUpdate();

            // 5. Kiểm tra kết quả
            if (rowsAffected == 0) {
                // Không có dòng nào bị ảnh hưởng
                // → Mã giường không tồn tại
                System.out.println("Không tìm thấy mã giường: " + bedId);
            } else {
                // Có ít nhất 1 dòng bị cập nhật
                System.out.println("Cập nhật trạng thái giường thành công");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. Đóng tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // executeUpdate() trả về kiểu int

// Ý nghĩa:
// → là số dòng (record) bị ảnh hưởng bởi câu lệnh SQL

// Ví dụ:
// UPDATE bed SET status = 'Đang sử dụng' WHERE id = 'Bed_001'
// → nếu tồn tại Bed_001 → trả về 1
// → nếu không tồn tại → trả về 0

// Vấn đề hiện tại:
// → khi cập nhật Bed_999 (không tồn tại)
// → executeUpdate() trả về 0 nhưng code không kiểm tra
// → hệ thống tưởng là thành công → gây hiểu nhầm

// Cách xử lý:
// → kiểm tra giá trị trả về
// → nếu == 0 → báo "Mã giường không tồn tại"
// → nếu > 0 → cập nhật thành công
}

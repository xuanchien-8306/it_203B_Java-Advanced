package Session_12.Bai_04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Xử lý insert hàng loạt kết quả xét nghiệm
public class LabResultService {

    public void insertBatchResults() {

        String sql = "INSERT INTO lab_results(patient_id, test_name, result_value) VALUES (?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // ================== PHẦN 1 - PHÂN TÍCH ==================
            // Nếu dùng Statement trong vòng lặp:
            // -> Mỗi lần chạy DB phải:
            //    1. Parse SQL (phân tích cú pháp)
            //    2. Tạo execution plan
            // => Lặp lại 1000 lần = cực kỳ tốn CPU + thời gian
            //
            // PreparedStatement:
            // -> Parse + compile 1 lần duy nhất
            // -> Tái sử dụng execution plan
            // => Giảm tải DB đáng kể

            long start = System.currentTimeMillis();

            for (int i = 1; i <= 1000; i++) {

                // ================== PHẦN 2 - TỐI ƯU ==================
                // Chỉ set giá trị, không tạo lại SQL
                pstmt.setInt(1, i);
                pstmt.setString(2, "Blood Test");
                pstmt.setDouble(3, 5.5 + i);

                pstmt.executeUpdate();
            }

            long end = System.currentTimeMillis();

            System.out.println("Insert completed in: " + (end - start) + " ms");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



package Session_13.Bai_01;

import Session_12.Bai_01.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CapPhatThuocService {

    public static void main(String[] args) {
        int patientId = 101;
        int medicineId = 1;

        capPhatThuoc(patientId, medicineId);
    }

    public static void capPhatThuoc(int patientId, int medicineId) {
        Connection conn = null;
        PreparedStatement updateStmt = null;
        PreparedStatement insertStmt = null;

        try {
            conn = DBContext.getConnection();

            /*
             * ================== PHÂN TÍCH LỖI (TRONG CODE) ==================
             * Mặc định JDBC sử dụng Auto-Commit = TRUE
             * => Mỗi lệnh executeUpdate() sẽ tự động COMMIT ngay sau khi chạy xong
             *
             * Vấn đề:
             * 1. UPDATE kho chạy thành công -> đã bị commit ngay
             * 2. INSERT lịch sử bị lỗi (mạng, DB, timeout...)
             * => Không có rollback xảy ra
             *
             * Kết quả:
             * - Kho bị trừ
             * - Không có lịch sử
             * => Vi phạm tính nguyên tử (Atomicity)
             */

            //  FIX: Tắt auto-commit để gom thành 1 transaction
            conn.setAutoCommit(false);

            //  TRỪ KHO
            String updateSQL = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            updateStmt = conn.prepareStatement(updateSQL);
            updateStmt.setInt(1, medicineId);

            int rowsAffected = updateStmt.executeUpdate();

            // Nếu không có dòng nào bị ảnh hưởng -> lỗi nghiệp vụ
            if (rowsAffected == 0) {
                throw new SQLException("Không tìm thấy thuốc!");
            }

            //  GHI LỊCH SỬ
            String insertSQL = "INSERT INTO Prescription_History (patient_id, medicine_id) VALUES (?, ?)";
            insertStmt = conn.prepareStatement(insertSQL);
            insertStmt.setInt(1, patientId);
            insertStmt.setInt(2, medicineId);

            insertStmt.executeUpdate();

            //  COMMIT
            // Nếu cả 2 bước OK -> commit 1 lần
            conn.commit();

            System.out.println("Cấp phát thuốc thành công!");

        } catch (Exception e) {

            /*
             * Nếu có lỗi xảy ra ở bất kỳ bước nào:
             * -> rollback toàn bộ transaction
             * => Đảm bảo:
             *    - Không bị trừ kho nếu chưa ghi lịch sử
             *    - Dữ liệu luôn nhất quán
             */
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Đã rollback do lỗi!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {
            try {
                if (updateStmt != null) updateStmt.close();
                if (insertStmt != null) insertStmt.close();

                if (conn != null) {
                    // Reset lại trạng thái ban đầu (best practice)
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
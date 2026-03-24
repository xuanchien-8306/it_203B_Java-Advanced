package Session_13.Bai_02;

import Session_13.Bai_01.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ThanhToanVienPhiService {

    public static void main(String[] args) {
        thanhToan(101, 1, 200000);
    }

    public static void thanhToan(int patientId, int invoiceId, double amount) {
        Connection conn = null;
        PreparedStatement deductStmt = null;
        PreparedStatement updateInvoiceStmt = null;

        try {
            conn = DB.getConnection();

            // Tắt auto-commit để bắt đầu transaction
            conn.setAutoCommit(false);

            /*
             * ================== PHÂN TÍCH LỖI ==================
             * Dev cũ đã:
             *  - setAutoCommit(false)
             *  - commit() ở cuối
             *
             * Nhưng trong catch chỉ:
             *  - System.out.println lỗi
             *
             * => VẤN ĐỀ:
             * Khi xảy ra SQLException:
             *   - Transaction đang mở (chưa commit, chưa rollback)
             *   - Connection giữ trạng thái "dang dở"
             *
             * HẬU QUẢ:
             *   - Lock chưa được giải phóng
             *   - Connection bị treo (dirty transaction)
             *   - Ảnh hưởng các transaction khác
             *
             * => Vi phạm nguyên tắc Transaction:
             *    BẮT BUỘC phải có rollback khi lỗi xảy ra
             */

            // ================== STEP 1: TRỪ TIỀN ==================
            String deductSQL = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            deductStmt = conn.prepareStatement(deductSQL);
            deductStmt.setDouble(1, amount);
            deductStmt.setInt(2, patientId);

            int rows = deductStmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Không tìm thấy ví bệnh nhân!");
            }

            // ================== STEP 2: CẬP NHẬT HÓA ĐƠN ==================
            String updateInvoiceSQL = "UPDATE Invoices SET status = 'PAID' WHERE invoice_id = ?";
            updateInvoiceStmt = conn.prepareStatement(updateInvoiceSQL);
            updateInvoiceStmt.setInt(1, invoiceId);

            updateInvoiceStmt.executeUpdate();

            // ================== COMMIT ==================
            conn.commit();
            System.out.println("Thanh toán thành công!");

        } catch (Exception e) {

            /*
             * ================== FIX QUAN TRỌNG ==================
             * Khi có lỗi:
             * => BẮT BUỘC phải rollback transaction
             * => Đưa DB về trạng thái trước khi bắt đầu giao dịch
             */
            try {
                if (conn != null) {
                    conn.rollback(); //  DÒNG QUAN TRỌNG NHẤT BỊ THIẾU
                    System.out.println("Đã rollback do lỗi!");
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

            e.printStackTrace();

        } finally {
            try {
                if (deductStmt != null) deductStmt.close();
                if (updateInvoiceStmt != null) updateInvoiceStmt.close();

                if (conn != null) {
                    conn.setAutoCommit(true); // reset lại
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

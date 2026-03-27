package Session_13.Bai_03;

import Session_13.Bai_01.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        try {
            conn = DB.getConnection(); // mở kết nối DB
            conn.setAutoCommit(false); // tắt auto commit để gom nhiều lệnh thành 1 transaction

            String sqlCheck = "select balance from Patient_Wallet where patient_id = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, maBenhNhan);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) throw new RuntimeException("Benh nhan khong ton tai"); // bẫy: không có bệnh nhân

            double balance = rs.getDouble("balance");
            if (balance < tienVienPhi) throw new RuntimeException("Khong du tien"); // bẫy: không đủ tiền → chặn giao dịch

            String sqlUpdateWallet = "update Patient_Wallet set balance = balance - ? where patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sqlUpdateWallet);
            ps1.setDouble(1, tienVienPhi);
            ps1.setInt(2, maBenhNhan);
            int r1 = ps1.executeUpdate();
            if (r1 == 0) throw new RuntimeException("Update wallet that bai"); // bẫy: không update được dòng nào

            String sqlUpdateBed = "update Beds set status = 'TRONG' where patient_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sqlUpdateBed);
            ps2.setInt(1, maBenhNhan);
            int r2 = ps2.executeUpdate();
            if (r2 == 0) throw new RuntimeException("Update bed that bai"); // bẫy: không tìm thấy giường

            String sqlUpdatePatient = "update Patients set status = 'DA_XUAT_VIEN' where id = ?";
            PreparedStatement ps3 = conn.prepareStatement(sqlUpdatePatient);
            ps3.setInt(1, maBenhNhan);
            int r3 = ps3.executeUpdate();
            if (r3 == 0) throw new RuntimeException("Update patient that bai"); // bẫy: không update được bệnh nhân

            conn.commit(); // tất cả thành công → lưu toàn bộ thay đổi xuống DB
            System.out.println("Xuat vien thanh cong");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // có lỗi → hoàn tác toàn bộ (All or Nothing)
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Co loi: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); // trả lại trạng thái mặc định cho connection
                if (conn != null) conn.close(); // đóng kết nối tránh leak tài nguyên
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        xuatVienVaThanhToan(1, 500000); // test hàm
    }
}
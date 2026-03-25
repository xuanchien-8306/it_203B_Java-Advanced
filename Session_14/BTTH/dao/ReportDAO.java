package Session_14.BTTH.dao;

import Session_14.BTTH.utils.DataConnect;

import java.sql.*;

public class ReportDAO {

    // =========================
    // lấy top người mua nhiều nhất
    // =========================
    public void topBuyers() {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // mở kết nối DB
            conn = DataConnect.openConnection();

            // gọi stored procedure (không có tham số)
            cs = conn.prepareCall("{call SP_GetTopBuyers()}");

            // thực thi và nhận kết quả
            rs = cs.executeQuery();

            // duyệt từng dòng dữ liệu trả về
            while (rs.next()) {
                // getInt(1): user_id
                // getString(2): name (tuỳ SP bạn viết)
                // getInt(3): total purchased
                System.out.println(
                        rs.getInt(1) + " " +
                                rs.getString(2) + " " +
                                rs.getInt(3)
                );
            }

        } catch (Exception e) {
            // log lỗi rõ ràng
            System.out.println("Lỗi topBuyers: " + e.getMessage());
        } finally {
            // đóng tài nguyên tránh leak connection
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (cs != null) cs.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }

    // =========================
    // tính tổng doanh thu
    // =========================
    public double totalRevenue() {
        Connection conn = null;
        CallableStatement cs = null;

        try {
            // mở kết nối
            conn = DataConnect.openConnection();

            // gọi function (có return value)
            // dấu ? ở đầu là giá trị trả về
            cs = conn.prepareCall("{?=call FUNC_TotalRevenue()}");

            // đăng ký kiểu dữ liệu OUT (return)
            cs.registerOutParameter(1, Types.DOUBLE);

            // thực thi
            cs.execute();

            // lấy giá trị trả về
            double result = cs.getDouble(1);

            // validate: nếu null DB trả về thì mặc định = 0
            if (cs.wasNull()) return 0;

            return result;

        } catch (Exception e) {
            System.out.println("Lỗi totalRevenue: " + e.getMessage());
            return 0;
        } finally {
            // đóng resource
            try { if (cs != null) cs.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}
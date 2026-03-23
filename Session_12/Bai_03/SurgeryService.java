package Session_12.Bai_03;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

// Logic gọi Stored Procedure
public class SurgeryService {

    public void getSurgeryFee(int surgeryId) {

        String sql = "{CALL GET_SURGERY_FEE(?, ?)}"; // 1 IN, 1 OUT

        try (Connection conn = DB.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // ================== PHẦN 1 - PHÂN TÍCH ==================
            // Vì sao phải registerOutParameter() trước execute?
            // 1. JDBC driver cần biết trước kiểu dữ liệu của tham số OUT
            // 2. Dựa vào đó driver cấp phát bộ nhớ và chuẩn bị vùng nhận dữ liệu
            // 3. Nếu không đăng ký -> driver không biết đọc dữ liệu trả về
            //    -> gây lỗi: "column index out of range" hoặc không lấy được giá trị
            //
            // Kiểu DECIMAL trong SQL ánh xạ sang Java:
            // -> phải dùng hằng số: Types.DECIMAL

            // Thiết lập tham số
            cstmt.setInt(1, surgeryId);                 // IN
            cstmt.registerOutParameter(2, Types.DECIMAL); // OUT (QUAN TRỌNG)

            // Thực thi
            cstmt.execute();

            // Lấy OUT (dùng BigDecimal cho tiền tệ chuẩn hơn)
            BigDecimal totalCost = cstmt.getBigDecimal(2);

            System.out.println("Total surgery cost: " + totalCost);

        } catch (SQLException e) {
            System.out.println("[ERROR] Cannot get surgery fee");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

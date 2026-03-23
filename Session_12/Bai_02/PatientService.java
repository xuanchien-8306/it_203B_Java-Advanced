package Session_12.Bai_02;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientService {

    // Cập nhật nhiệt độ và nhịp tim
    public void updateVitalSigns(int id, double temperature, int heartRate) {

        String sql = "UPDATE patients SET temperature = ?, heart_rate = ? WHERE id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Phân tích
            // setDouble(), setInt() giúp KHÔNG phụ thuộc dấu . hay , vì:
            // 1. Không nối chuỗi SQL như Statement
            // 2. Dữ liệu được truyền qua placeholder (?)
            // 3. JDBC driver gửi dữ liệu dạng binary (đúng kiểu double/int)
            // 4. Database nhận trực tiếp giá trị -> không cần parse chuỗi
            // => Không bị ảnh hưởng bởi Locale (37.5 hay 37,5 đều đúng)

            pstmt.setDouble(1, temperature);
            pstmt.setInt(2, heartRate);
            pstmt.setInt(3, id);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("[SUCCESS] Updated!");
            } else {
                System.out.println("[WARNING] Not found!");
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Update failed!");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

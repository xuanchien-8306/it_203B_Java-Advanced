package business;

import utils.DBConnection;
import java.sql.*;

public class TableBusiness {

    // 1. Xem toàn bộ danh sách bàn
    public void displayAllTables() {
        String sql = "SELECT * FROM restaurant_tables";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("+----+----------+----------+-----------+");
            System.out.printf("| %-2s | %-8s | %-8s | %-9s |\n", "ID", "Số Bàn", "Sức Chứa", "Trạng Thái");
            System.out.println("+----+----------+----------+-----------+");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("| %-2d | %-8d | %-8d | %-9s |\n",
                        rs.getInt("id"), rs.getInt("table_number"), rs.getInt("capacity"), rs.getString("status"));
            }
            if (!hasData) {
                System.out.println("| Chưa có dữ liệu bàn ăn nào!            |");
            }
            System.out.println("+----+----------+----------+-----------+");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 2. Tìm kiếm bàn theo số bàn
    public void searchTable(String keyword) {
        // Ép kiểu table_number thành chuỗi (CHAR) để có thể dùng toán tử LIKE tìm kiếm tương đối
        String sql = "SELECT * FROM restaurant_tables WHERE CAST(table_number AS CHAR) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            System.out.println("+----+----------+----------+-----------+");
            System.out.printf("| %-2s | %-8s | %-8s | %-9s |\n", "ID", "Số Bàn", "Sức Chứa", "Trạng Thái");
            System.out.println("+----+----------+----------+-----------+");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("| %-2d | %-8d | %-8d | %-9s |\n",
                        rs.getInt("id"), rs.getInt("table_number"), rs.getInt("capacity"), rs.getString("status"));
            }
            if (!hasData) {
                System.out.println("| Không tìm thấy bàn ăn phù hợp!           |");
            }
            System.out.println("+----+----------+----------+-----------+");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 3. Kiểm tra ID bàn có tồn tại không (Dùng cho Sửa/Xóa)
    public boolean isTableIdExists(int id) {
        String sql = "SELECT id FROM restaurant_tables WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu ID có tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 4. Kiểm tra Số bàn đã tồn tại chưa (Dùng cho Thêm/Sửa)
    // - excludeId: Dùng khi Sửa, truyền vào ID của bàn đang sửa để không bị check trùng với chính nó. (Khi Thêm truyền -1)
    public boolean isTableNumberExists(int tableNumber, int excludeId) {
        String sql = "SELECT id FROM restaurant_tables WHERE table_number = ? AND id != ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tableNumber);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu số bàn đã bị người khác lấy
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 5. Thêm bàn mới
    public boolean addTable(int tableNumber, int capacity) {
        String sql = "INSERT INTO restaurant_tables (table_number, capacity) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tableNumber);
            ps.setInt(2, capacity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("[!] Lỗi: Số bàn có thể đã tồn tại!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 6. Cập nhật thông tin bàn
    public boolean updateTable(int id, int tableNumber, int capacity) {
        String sql = "UPDATE restaurant_tables SET table_number = ?, capacity = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tableNumber);
            ps.setInt(2, capacity);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 7. Xóa bàn
    public boolean deleteTable(int id) {
        String sql = "DELETE FROM restaurant_tables WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Lỗi: Không thể xóa vì bàn này đã được gắn vào Order của khách hàng!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
package business;

import utils.DBConnection;
import java.sql.*;

public class MenuBusiness {

    // Kiểm tra tên món ăn đã tồn tại chưa (Chống trùng lặp)
    public boolean isMenuItemNameExists(String name) {
        String sql = "SELECT id FROM menu_items WHERE LOWER(name) = LOWER(?) AND is_available = TRUE";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            return ps.executeQuery().next();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 1. Hiển thị và tìm kiếm
    public void displayMenu(String keyword) {
        String sql = "SELECT * FROM menu_items WHERE is_available = TRUE";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND name LIKE ?";
        }

        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            java.sql.ResultSet rs = ps.executeQuery();
            System.out.println("+----+----------------------+--------------+-------+---------+");
            System.out.printf("| %-2s | %-20s | %-12s | %-5s | %-7s |\n", "ID", "Tên món", "Giá (VNĐ)", "Loại", "Tồn kho");
            System.out.println("+----+----------------------+--------------+-------+---------+");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String type = rs.getString("type");
                String stock = type.equals("DRINK") ? String.valueOf(rs.getInt("stock")) : "-";
                System.out.printf("| %-2d | %-20s | %-12.2f | %-5s | %-7s |\n",
                        rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), type, stock);
            }

            // Fix lỗi hiển thị dính chữ ở đây: Bắt buộc dùng println
            if (!hasData) {
                System.out.println("| Không tìm thấy món ăn nào phù hợp!                         |");
            }
            System.out.println("+----+----------------------+--------------+-------+---------+");
            System.out.flush(); // Đẩy dữ liệu ra màn hình ngay lập tức để không bị JLine đè

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 2. Bổ sung hàm kiểm tra ID tồn tại (Dùng cho Sửa/Xóa)
    public boolean isMenuItemExists(int id) {
        String sql = "SELECT id FROM menu_items WHERE id = ? AND is_available = TRUE";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean addMenuItem(String name, double price, int catId, String type, int stock) {
        String sql = "INSERT INTO menu_items (name, price, category_id, type, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, catId);
            ps.setString(4, type);
            if (type.equals("DRINK")) ps.setInt(5, stock);
            else ps.setNull(5, Types.INTEGER);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updateMenuItemFull(int id, String name, double price, int catId, String type, int stock) {
        String sql = "UPDATE menu_items SET name = ?, price = ?, category_id = ?, type = ?, stock = ? WHERE id = ?";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, catId);
            ps.setString(4, type);
            if (type.equals("DRINK")) {
                ps.setInt(5, stock);
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean deleteMenuItem(int id) {
        String sql = "UPDATE menu_items SET is_available = FALSE WHERE id = ?"; // Xóa mềm (Soft delete) để không mất lịch sử hóa đơn
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
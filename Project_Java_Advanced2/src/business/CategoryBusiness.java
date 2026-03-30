package business;

import entity.Category;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryBusiness {

    // Kiểm tra ID phân loại có tồn tại không
    public boolean isCategoryIdExists(int id) {
        String sql = "SELECT id FROM categories WHERE id = ?";
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

    // 1. READ (Xem danh sách)
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // 2. CREATE (Thêm mới)
    public boolean addCategory(String name) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 3. UPDATE (Cập nhật)
    public boolean updateCategory(int id, String newName) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 4. DELETE (Xóa)
    public boolean deleteCategory(int id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Bắt lỗi nếu danh mục này đang có món ăn (dính khóa ngoại)
            System.err.println("[!] Không thể xóa danh mục đang chứa món ăn!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 5. Kiểm tra tên phân loại đã tồn tại chưa (Chống trùng lặp)
    public boolean isCategoryNameExists(String name) {
        // Dùng LOWER để so sánh không phân biệt hoa thường (VD: 'Gà' và 'gà' là trùng)
        String sql = "SELECT id FROM categories WHERE LOWER(name) = LOWER(?)";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            java.sql.ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu đã có trong DB
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // 6. Tìm kiếm phân loại theo tên
    public java.util.List<entity.Category> searchCategories(String keyword) {
        java.util.List<entity.Category> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM categories WHERE name LIKE ?";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new entity.Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
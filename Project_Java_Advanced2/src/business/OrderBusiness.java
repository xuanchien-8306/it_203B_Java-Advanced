package business;

import utils.DBConnection;
import java.sql.*;

public class OrderBusiness {

    // [Khách hàng] Đặt món
    public boolean placeOrder(int customerId, int tableId, int menuItemId, int quantity, double priceAtOrder) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // 1. Tạo Order mới
            String sqlOrder = "INSERT INTO orders (customer_id, status) VALUES (?, 'PENDING')";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, customerId);
            psOrder.executeUpdate();

            ResultSet rsKeys = psOrder.getGeneratedKeys();
            int orderId = 0;
            if (rsKeys.next()) orderId = rsKeys.getInt(1);

            // 2. Gắn Bàn vào Order
            String sqlTable = "INSERT INTO order_tables (order_id, table_id) VALUES (?, ?)";
            PreparedStatement psTable = conn.prepareStatement(sqlTable);
            psTable.setInt(1, orderId);
            psTable.setInt(2, tableId);
            psTable.executeUpdate();

            // 3. Thêm Món vào Order Items
            String sqlItem = "INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, status) VALUES (?, ?, ?, ?, 'PENDING')";
            PreparedStatement psItem = conn.prepareStatement(sqlItem);
            psItem.setInt(1, orderId);
            psItem.setInt(2, menuItemId);
            psItem.setInt(3, quantity);
            psItem.setDouble(4, priceAtOrder);
            psItem.executeUpdate();

            // 4. Update trạng thái bàn thành OCCUPIED
            String sqlUpdateTable = "UPDATE restaurant_tables SET status = 'OCCUPIED' WHERE id = ?";
            PreparedStatement psUpdateTbl = conn.prepareStatement(sqlUpdateTable);
            psUpdateTbl.setInt(1, tableId);
            psUpdateTbl.executeUpdate();

            conn.commit(); // Lưu thay đổi
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // [Khách hàng] Xem món đã đặt
    public void viewMyOrders(int customerId) {
        String sql = "SELECT oi.id, m.name, oi.quantity, oi.status FROM order_items oi " +
                "JOIN orders o ON oi.order_id = o.id " +
                "JOIN menu_items m ON oi.menu_item_id = m.id WHERE o.customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            System.out.println("+----+----------------------+----+-----------+");
            System.out.printf("| %-2s | %-20s | %-2s | %-9s |\n", "ID", "Tên Món", "SL", "Trạng Thái");
            System.out.println("+----+----------------------+----+-----------+");
            while (rs.next()) {
                System.out.printf("| %-2d | %-20s | %-2d | %-9s |\n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getString("status"));
            }
            System.out.println("+----+----------------------+----+-----------+");
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // [Khách hàng] Hủy món
    public boolean cancelOrderItem(int orderItemId) {
        String sqlCheck = "SELECT status FROM order_items WHERE id = ?";
        String sqlUpdate = "UPDATE order_items SET status = 'CANCELLED' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, orderItemId);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getString("status").equals("PENDING")) {
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, orderItemId);
                return psUpdate.executeUpdate() > 0;
            } else {
                System.err.println("[!] Từ chối hủy: Món ăn đang được nấu hoặc đã phục vụ!");
            }
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // [Đầu bếp] Xem danh sách cần nấu
    public void getChefTasks() {
        String sql = "SELECT oi.id, m.name, oi.quantity, oi.status FROM order_items oi " +
                "JOIN menu_items m ON oi.menu_item_id = m.id WHERE oi.status IN ('PENDING', 'COOKING')";
        // Giống format in bảng ở trên... (Mình viết tắt để tiết kiệm không gian nhé)
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- BẢNG THEO DÕI BẾP ---");
            System.out.printf("| %-4s | %-20s | %-2s | %-9s |\n", "ID", "Tên Món", "SL", "Trạng Thái");
            while (rs.next()) {
                System.out.printf("| %-4d | %-20s | %-2d | %-9s |\n", rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getString("status"));
            }
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // [Đầu bếp] Cập nhật trạng thái
    public boolean updateChefStatus(int orderItemId, String newStatus) {
        String sql = "UPDATE order_items SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    // ==================================================
    // PHÂN HỆ ĐẦU BẾP (CHEF)
    // ==================================================

    // 1. Hiển thị danh sách món đang được gọi
    public void displayChefTasks() {
        String sql = "SELECT oi.id, m.name, oi.quantity, oi.status " +
                "FROM order_items oi " +
                "JOIN menu_items m ON oi.menu_item_id = m.id " +
                "WHERE oi.status IN ('PENDING', 'COOKING', 'READY') " +
                "ORDER BY oi.id ASC";

        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- DANH SÁCH MÓN KHÁCH ĐANG GỌI ---");
            System.out.println("+----+----------------------+----+-----------+");
            System.out.printf("| %-2s | %-20s | %-2s | %-9s |\n", "ID", "Tên Món", "SL", "Trạng Thái");
            System.out.println("+----+----------------------+----+-----------+");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("| %-2d | %-20s | %-2d | %-9s |\n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getString("status"));
            }
            if (!hasData) {
                System.out.println("| Hiện tại không có món nào cần chuẩn bị!    |");
            }
            System.out.println("+----+----------------------+----+-----------+");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 2. Lấy trạng thái hiện tại của 1 món
    public String getOrderItemStatus(int orderItemId) {
        String sql = "SELECT status FROM order_items WHERE id = ?";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 3. Cập nhật trạng thái
    public boolean updateOrderStatus(int orderItemId, String newStatus) {
        String sql = "UPDATE order_items SET status = ? WHERE id = ?";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderItemId);
            return ps.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
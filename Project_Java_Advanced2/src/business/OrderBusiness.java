package business;

import utils.DBConnection;
import java.sql.*;
import java.util.List;

public class OrderBusiness {

    // ==================================================
    // PHÂN HỆ KHÁCH HÀNG (CUSTOMER)
    // ==================================================

    // 1. [Khách hàng] Đặt NHIỀU món cùng lúc (Dùng Transaction)
    public boolean placeOrderMultiple(int customerId, int tableId, List<Integer> itemIds, List<Integer> quantities, List<Double> prices) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // Tạo Order mới
            String sqlOrder = "INSERT INTO orders (customer_id, status) VALUES (?, 'PENDING')";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, customerId);
            psOrder.executeUpdate();

            ResultSet rsKeys = psOrder.getGeneratedKeys();
            int orderId = 0;
            if (rsKeys.next()) orderId = rsKeys.getInt(1);

            // Gắn Bàn vào Order
            String sqlTable = "INSERT INTO order_tables (order_id, table_id) VALUES (?, ?)";
            PreparedStatement psTable = conn.prepareStatement(sqlTable);
            psTable.setInt(1, orderId);
            psTable.setInt(2, tableId);
            psTable.executeUpdate();

            // Thêm danh sách món vào Order Items
            String sqlItem = "INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, status) VALUES (?, ?, ?, ?, 'PENDING')";
            PreparedStatement psItem = conn.prepareStatement(sqlItem);
            for (int i = 0; i < itemIds.size(); i++) {
                psItem.setInt(1, orderId);
                psItem.setInt(2, itemIds.get(i));
                psItem.setInt(3, quantities.get(i));
                psItem.setDouble(4, prices.get(i));
                psItem.addBatch();
            }
            psItem.executeBatch();

            // Update trạng thái bàn thành OCCUPIED
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

    // 1. Lấy danh sách SỐ BÀN các bàn mà Khách hàng này đang sử dụng (chưa thanh toán)
    public java.util.List<Integer> getActiveTableNumbers(int customerId) {
        java.util.List<Integer> activeTables = new java.util.ArrayList<>();
        String sql = "SELECT t.table_number FROM orders o " +
                "JOIN order_tables ot ON o.id = ot.order_id " +
                "JOIN restaurant_tables t ON ot.table_id = t.id " +
                "WHERE o.customer_id = ? AND o.status NOT IN ('COMPLETED', 'CANCELLED')";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                activeTables.add(rs.getInt("table_number"));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return activeTables;
    }

    // 2. Lấy Order ID của 1 SỐ BÀN cụ thể thuộc về Khách hàng này
    public int getActiveOrderIdByTableNumber(int customerId, int tableNumber) {
        String sql = "SELECT o.id FROM orders o " +
                "JOIN order_tables ot ON o.id = ot.order_id " +
                "JOIN restaurant_tables t ON ot.table_id = t.id " +
                "WHERE o.customer_id = ? AND t.table_number = ? AND o.status NOT IN ('COMPLETED', 'CANCELLED')";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, tableNumber);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) { throw new RuntimeException(e); }
        return -1;
    }

    // Thêm danh sách món vào trực tiếp một Đơn hàng đã có sẵn (Gọi thêm món)
    public boolean addItemsToExistingOrder(int orderId, List<Integer> itemIds, List<Integer> quantities, List<Double> prices) {
        String sqlItem = "INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, status) VALUES (?, ?, ?, ?, 'PENDING')";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement psItem = conn.prepareStatement(sqlItem)) {

            for (int i = 0; i < itemIds.size(); i++) {
                psItem.setInt(1, orderId);
                psItem.setInt(2, itemIds.get(i));
                psItem.setInt(3, quantities.get(i));
                psItem.setDouble(4, prices.get(i));
                psItem.addBatch();
            }
            psItem.executeBatch();
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 2. [Khách hàng] Xem món đã đặt
    public void viewMyOrders(int customerId) {
        String sql = "SELECT oi.id, m.name, oi.quantity, oi.status, oi.price_at_order FROM order_items oi " +
                "JOIN orders o ON oi.order_id = o.id " +
                "JOIN menu_items m ON oi.menu_item_id = m.id WHERE o.customer_id = ? ORDER BY oi.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            System.out.println("╭────┬──────────────────────┬────┬──────────────┬────────────╮");
            System.out.printf ("│ %-2s │ %-20s │ %-2s │ %-12s │ %-9s │\n", "ID", "Tên Món", "SL", "Giá/VNĐ", "Trạng Thái");
            System.out.println("├────┼──────────────────────┼────┼──────────────┼────────────┤");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("| %-2d | %-20s | %-2d | %-12.2f | %-9s  |\n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getDouble("price_at_order"), rs.getString("status"));
            }
            if (!hasData) System.out.println("| Bạn chưa gọi món nào!                                     |");
            System.out.println("╰────┴──────────────────────┴────┴──────────────┴────────────╯");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 3. [Khách hàng] Hủy món
    public boolean cancelOrderItem(int orderItemId, int customerId) {
        String sqlCheck = "SELECT oi.status FROM order_items oi JOIN orders o ON oi.order_id = o.id WHERE oi.id = ? AND o.customer_id = ?";
        String sqlUpdate = "UPDATE order_items SET status = 'CANCELLED' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, orderItemId);
            psCheck.setInt(2, customerId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                if (rs.getString("status").equals("PENDING")) {
                    PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                    psUpdate.setInt(1, orderItemId);
                    return psUpdate.executeUpdate() > 0;
                } else {
                    System.err.println("[!] Từ chối hủy: Món này Đầu bếp đang làm hoặc đã phục vụ rồi!");
                    return false;
                }
            } else {
                System.err.println("[!] Lỗi: ID món không tồn tại hoặc không thuộc về đơn hàng của bạn!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    // ==================================================
    // PHÂN HỆ ĐẦU BẾP (CHEF)
    // ==================================================

    // 4. Hiển thị danh sách món đang được gọi
    public void displayChefTasks() {
        String sql = "SELECT oi.id, m.name, oi.quantity, oi.status " +
                "FROM order_items oi " +
                "JOIN menu_items m ON oi.menu_item_id = m.id " +
                "WHERE oi.status IN ('PENDING', 'COOKING', 'READY') " +
                "ORDER BY oi.id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n╭─────────────────────────────────────────────╮");
            System.out.println("│     DANH SÁCH MÓN KHÁCH ĐANG GỌI            │");
            System.out.println("├────┬──────────────────────┬────┬────────────┤");
            System.out.printf ("│ %-2s │ %-20s │ %-2s │ %-9s │\n", "ID", "Tên Món", "SL", "Trạng Thái");
            System.out.println("├────┼──────────────────────┼────┼────────────┤");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                System.out.printf("│ %-2d │ %-20s │ %-2d │ %-9s  │\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("status"));
            }

            if (!hasData) {
                System.out.printf("│ %-44s │\n", "Hiện tại không có món nào cần chuẩn bị!");
            }

            System.out.println("╰────┴──────────────────────┴────┴────────────╯");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 5. Lấy trạng thái hiện tại của 1 món
    public String getOrderItemStatus(int orderItemId) {
        String sql = "SELECT status FROM order_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 6. Cập nhật trạng thái
    public boolean updateOrderStatus(int orderItemId, String newStatus) {
        String sql = "UPDATE order_items SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // ==================================================
    // PHÂN HỆ THANH TOÁN & HÓA ĐƠN (MANAGER/CASHIER)
    // ==================================================

    // Kiểm tra xem đơn hàng đã được phục vụ XONG HẾT chưa
    public boolean isOrderFullyServed(int orderId) {
        // Tìm xem có món nào đang ở trạng thái PENDING, COOKING hoặc READY không
        String sql = "SELECT id FROM order_items WHERE order_id = ? AND status IN ('PENDING', 'COOKING', 'READY')";
        try (java.sql.Connection conn = utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            java.sql.ResultSet rs = ps.executeQuery();
            // Nếu KHÔNG CÓ món nào lọt vào danh sách trên -> Trả về true (Đã xong hết)
            return !rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 7. Hiển thị danh sách các bàn ĐANG CÓ KHÁCH (OCCUPIED)
    public boolean displayOccupiedTables() {
        String sql = "SELECT t.id, t.table_number, o.id as order_id, u.full_name " +
                "FROM restaurant_tables t " +
                "JOIN order_tables ot ON t.id = ot.table_id " +
                "JOIN orders o ON ot.order_id = o.id " +
                "JOIN users u ON o.customer_id = u.id " +
                "WHERE t.status = 'OCCUPIED' AND o.status NOT IN ('COMPLETED', 'CANCELLED')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("╭─────────────────────────────────────────────────────╮");
            System.out.println("│               DANH SÁCH BÀN ĐANG SỬ DỤNG            │");
            System.out.println("├────┬──────────┬─────────┬───────────────────────────┤");
            System.out.printf ("│ %-2s │ %-8s │ %-7s │ %-25s │\n", "ID", "Số Bàn", "Mã Đơn", "Khách Hàng");
            System.out.println("├────┼──────────┼─────────┼───────────────────────────┤");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                System.out.printf("│ %-2d │ %-8d │ %-7d │ %-25s │\n",
                        rs.getInt("id"),
                        rs.getInt("table_number"),
                        rs.getInt("order_id"),
                        rs.getString("full_name"));
            }

            if (!hasData) {
                System.out.printf("│ %-60s │\n", "Hiện tại không có bàn nào đang sử dụng!");
                System.out.println("╰────┴──────────┴─────────┴───────────────────────────╯");
                return false;
            }

            System.out.println("╰────┴──────────┴─────────┴───────────────────────────╯");
            return true;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    // 8. In Hóa đơn tạm tính theo SỐ BÀN
    public double[] printBillAndGetTotal(int tableNumber) {
        // 8.1. Tìm Order ID đang active của SỐ BÀN này (JOIN thêm bảng t)
        int orderId = -1;
        String sqlOrder = "SELECT o.id FROM orders o " +
                "JOIN order_tables ot ON o.id = ot.order_id " +
                "JOIN restaurant_tables t ON ot.table_id = t.id " +
                "WHERE t.table_number = ? AND o.status NOT IN ('COMPLETED', 'CANCELLED')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlOrder)) {
            ps.setInt(1, tableNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) orderId = rs.getInt("id");
        } catch (Exception e) { throw new RuntimeException(e); }

        if (orderId == -1) return null; // Không tìm thấy hóa đơn

        // 8.2. Lấy chi tiết món ăn (Bỏ qua các món đã CANCELLED) và tính tổng tiền
        double totalAmount = 0;
        String sqlItems = "SELECT m.name, oi.quantity, oi.price_at_order FROM order_items oi JOIN menu_items m ON oi.menu_item_id = m.id WHERE oi.order_id = ? AND oi.status != 'CANCELLED'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlItems)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n=================================================");
            System.out.println("                 HÓA ĐƠN THANH TOÁN              ");
            System.out.println("   Bàn số: " + tableNumber + "  |  Mã Đơn Hàng: " + orderId);
            System.out.println("-------------------------------------------------");
            System.out.printf("%-22s | %-5s | %-15s\n", "Tên món", "SL", "Thành tiền (VNĐ)");
            System.out.println("-------------------------------------------------");

            while (rs.next()) {
                String name = rs.getString("name");
                int qty = rs.getInt("quantity");
                double price = rs.getDouble("price_at_order");
                double lineTotal = qty * price;
                totalAmount += lineTotal;

                System.out.printf("%-22s | %-5d | %-15.2f\n", name, qty, lineTotal);
            }
            System.out.println("-------------------------------------------------");
            System.out.printf("TỔNG CỘNG:                           %,.2f VNĐ\n", totalAmount);
            System.out.println("=================================================");

            return new double[]{orderId, totalAmount};
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    // 9. Thực hiện Thanh toán theo SỐ BÀN
    public boolean processCheckout(int tableNumber, int orderId, double totalAmount) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Dùng Transaction

            // B1. Lưu vào bảng invoices
            String sqlInvoice = "INSERT INTO invoices (order_id, total_amount, paid_at) VALUES (?, ?, NOW())";
            try (PreparedStatement ps = conn.prepareStatement(sqlInvoice)) {
                ps.setInt(1, orderId);
                ps.setDouble(2, totalAmount);
                ps.executeUpdate();
            }

            // B2. Cập nhật bảng orders
            String sqlOrder = "UPDATE orders SET total_amount = ?, status = 'COMPLETED' WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlOrder)) {
                ps.setDouble(1, totalAmount);
                ps.setInt(2, orderId);
                ps.executeUpdate();
            }

            // B3. Giải phóng bàn (Cập nhật bảng restaurant_tables dựa trên SỐ BÀN)
            String sqlTable = "UPDATE restaurant_tables SET status = 'AVAILABLE' WHERE table_number = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlTable)) {
                ps.setInt(1, tableNumber);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
package Session_14.BTTH.service;

import Session_14.BTTH.dao.*;
import Session_14.BTTH.entity.OrderDetail;
import Session_14.BTTH.utils.DataConnect;

import java.sql.Connection;
import java.util.List;

public class OrderService {

    // trả về true = thành công, false = thất bại
    public boolean placeOrder(int userId, List<OrderDetail> items) {

        Connection conn = null;

        try {
            conn = DataConnect.openConnection();

            // bắt đầu transaction
            conn.setAutoCommit(false);

            // mức isolation cao nhất
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ProductDAO productDAO = new ProductDAO();
            OrderDAO orderDAO = new OrderDAO();
            OrderDetailDAO detailDAO = new OrderDetailDAO();
            UserDAO userDAO = new UserDAO();

            // validate user
            if (!userDAO.exists(userId, conn))
                throw new Exception("User không tồn tại");

            // validate list
            if (items == null || items.isEmpty())
                throw new Exception("Danh sách rỗng");

            // xử lý từng sản phẩm
            for (OrderDetail item : items) {

                if (item.getQuantity() <= 0)
                    throw new Exception("Quantity sai");

                // lock row
                int stock = productDAO.getStockForUpdate(item.getProductId(), conn);

                // check tồn kho
                if (stock < item.getQuantity())
                    throw new Exception("Hết hàng");

                // trừ kho
                productDAO.updateStock(
                        item.getProductId(),
                        stock - item.getQuantity(),
                        conn
                );
            }

            // tạo order
            int orderId = orderDAO.createOrder(userId, conn);

            // insert chi tiết
            detailDAO.insertBatch(orderId, items, conn);

            // commit
            conn.commit();

            System.out.println("SUCCESS user=" + userId);
            return true;

        } catch (Exception e) {

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ignored) {}

            System.out.println("FAIL user=" + userId + " | " + e.getMessage());
            return false;
        }
    }
}
package Session_14.BTTH.dao;

import Session_14.BTTH.entity.OrderDetail;
import java.sql.*;
import java.util.List;

public class OrderDetailDAO {

    // insert nhiều dòng bằng batch
    public void insertBatch(int orderId, List<OrderDetail> list, Connection conn) throws Exception {

        String sql = "insert into order_details(order_id,product_id,quantity,price) values(?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        for (OrderDetail od : list) {

            ps.setInt(1, orderId);
            ps.setInt(2, od.getProductId());
            ps.setInt(3, od.getQuantity());
            ps.setDouble(4, od.getPrice());

            // thêm vào batch
            ps.addBatch();
        }

        // chạy 1 lần
        ps.executeBatch();
    }
}
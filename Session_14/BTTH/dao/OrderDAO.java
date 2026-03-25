package Session_14.BTTH.dao;

import java.sql.*;

public class OrderDAO {

    // tạo order mới
    public int createOrder(int userId, Connection conn) throws Exception {

        String sql = "insert into orders(user_id) values(?)";

        // lấy id vừa insert
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, userId);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) return rs.getInt(1);

        throw new Exception("Không tạo được order");
    }
}
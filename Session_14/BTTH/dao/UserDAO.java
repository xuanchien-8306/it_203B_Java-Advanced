package Session_14.BTTH.dao;

import java.sql.*;

public class UserDAO {

    // kiểm tra user tồn tại
    public boolean exists(int userId, Connection conn) throws Exception {

        if (userId <= 0)
            throw new Exception("userId không hợp lệ");

        String sql = "select id from users where id=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        return ps.executeQuery().next();
    }
}
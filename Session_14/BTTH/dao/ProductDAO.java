package Session_14.BTTH.dao;

import java.sql.*;

public class ProductDAO {

    // thêm sản phẩm
    public void insert(String name, double price, int quantity, int categoryId, Connection conn) throws Exception {

        // validate dữ liệu
        if (name == null || name.isEmpty())
            throw new Exception("Tên sản phẩm rỗng");

        if (price <= 0)
            throw new Exception("Giá không hợp lệ");

        String sql = "insert into products(name,price,quantity,category_id) values(?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setDouble(2, price);
        ps.setInt(3, quantity);
        ps.setInt(4, categoryId);

        ps.executeUpdate();
    }

    // lấy tồn kho + khóa dòng
    public int getStockForUpdate(int productId, Connection conn) throws Exception {

        String sql = "select quantity from products where id=? for update";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, productId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) return rs.getInt(1);

        throw new Exception("Không có sản phẩm");
    }

    // cập nhật tồn kho
    public void updateStock(int id, int newStock, Connection conn) throws Exception {

        // không cho âm
        if (newStock < 0)
            throw new Exception("Stock âm");

        String sql = "update products set quantity=? where id=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, newStock);
        ps.setInt(2, id);

        ps.executeUpdate();
    }
}
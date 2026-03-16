package Session_07.Bai_04;

// Implementation lưu đơn hàng vào database
public class DatabaseOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {

        System.out.println("Lưu đơn hàng vào database: " + order.getOrderId());

    }

    @Override
    public void findAll() {

        System.out.println("Đang đọc danh sách đơn hàng từ database");

    }
}

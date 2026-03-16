package Session_07.Bai_01;

// Lớp lưu đơn hàng
public class OrderRepository {

    public void save(Order order) {

        System.out.println("Đã lưu đơn hàng " + order.getOrderId());
    }
}

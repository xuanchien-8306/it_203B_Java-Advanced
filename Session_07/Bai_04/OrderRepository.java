package Session_07.Bai_04;

// Interface định nghĩa cách lưu trữ đơn hàng
public interface OrderRepository {

    void save(Order order);

    void findAll();
}
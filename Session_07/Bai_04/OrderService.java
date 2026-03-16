package Session_07.Bai_04;

// Lớp xử lý đơn hàng (module cấp cao)
public class OrderService {

    private OrderRepository repository;
    private NotificationService notificationService;

    public OrderService(OrderRepository repository,
                        NotificationService notificationService) {

        this.repository = repository;
        this.notificationService = notificationService;
    }

    // Tạo đơn hàng mới
    public void createOrder(Order order) {

        // lưu đơn hàng
        repository.save(order);

        // gửi thông báo
        notificationService.send(
                "Đơn hàng " + order.getOrderId() + " đã được tạo",
                order.getCustomerEmail()
        );
    }
}

package Session_07.BTTH;
public class OrderProcessor {

    private Database database;
    private Notification notification;

    public OrderProcessor(Database database, Notification notification) {
        this.database = database;
        this.notification = notification;
    }

    public void processOrder(Order order, PaymentMethod paymentMethod) {

        // 1 lưu đơn hàng
        database.save(order);

        // 2 thanh toán
        paymentMethod.pay(order);

        // 3 gửi thông báo
        notification.send(
                order.getCustomerEmail(),
                "Đơn hàng của bạn đã xử lý thành công"
        );
    }
}




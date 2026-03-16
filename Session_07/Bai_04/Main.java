package Session_07.Bai_04;

public class Main {

    public static void main(String[] args) {

        // File + Email
        System.out.println("=== Dùng FileOrderRepository và EmailService ===");

        OrderRepository repo1 = new FileOrderRepository();
        NotificationService notify1 = new EmailService();

        OrderService orderService1 = new OrderService(repo1, notify1);

        Order order1 = new Order("ORD001", "a@example.com");

        System.out.println("Tạo đơn hàng " + order1.getOrderId());
        orderService1.createOrder(order1);


        System.out.println();


        // Database + SMS
        System.out.println("=== Dùng DatabaseOrderRepository và SMSNotification ===");

        OrderRepository repo2 = new DatabaseOrderRepository();
        NotificationService notify2 = new SMSNotification();

        OrderService orderService2 = new OrderService(repo2, notify2);

        Order order2 = new Order("ORD002", "b@example.com");

        System.out.println("Tạo đơn hàng " + order2.getOrderId());
        orderService2.createOrder(order2);

    }
}
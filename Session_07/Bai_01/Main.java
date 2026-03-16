package Session_07.Bai_01;
public class Main {

    public static void main(String[] args) {

        // tạo sản phẩm
        Product p1 = new Product("SP01", "Laptop", 15000000);
        Product p2 = new Product("SP02", "Chuột", 300000);

        System.out.println("Tạo sản phẩm: SP01 - Laptop - 15000000");
        System.out.println("Tạo sản phẩm: SP02 - Chuột - 300000");

        // tạo khách hàng
        Customer customer =
                new Customer("Nguyễn Văn A", "a@example.com", "Hà Nội");

        System.out.println("Đã thêm khách hàng");

        // tạo đơn hàng
        Order order = new Order("ORD001", customer);

        System.out.println("Đơn hàng ORD001 được tạo");

        order.addItem(p1, 1);
        order.addItem(p2, 2);

        // tính tổng tiền
        OrderCalculator calculator = new OrderCalculator();

        double total = calculator.calculateTotal(order);

        System.out.println("Tổng tiền: " + total);

        // lưu đơn hàng
        OrderRepository repository = new OrderRepository();
        repository.save(order);

        // gửi email
        EmailService emailService = new EmailService();
        emailService.sendConfirmation(order);
    }
}

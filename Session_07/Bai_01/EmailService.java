package Session_07.Bai_01;

// Lớp gửi email xác nhận
public class EmailService {

    public void sendConfirmation(Order order) {

        System.out.println(
                "Đã gửi email đến "
                        + order.getCustomer().getEmail()
                        + ": Đơn hàng "
                        + order.getOrderId()
                        + " đã được tạo"
        );
    }
}

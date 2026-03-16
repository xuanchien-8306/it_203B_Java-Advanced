package Session_07.BTTH;

public class Paypal implements PaymentMethod {

    @Override
    public void pay(Order order) {
        System.out.println("Thanh toán bằng PayPal");
    }
}

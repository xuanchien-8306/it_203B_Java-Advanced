package Session_07.BTTH;

public class CreditCard implements PaymentMethod {

    @Override
    public void pay(Order order) {
        System.out.println("Thanh toán bằng thẻ tín dụng");
    }
}

package Session_07.Bai_03;

// Interface thanh toán thẻ tín dụng
public interface CardPayable extends PaymentMethod {

    void processCreditCard(double amount);

}

package Session_07.Bai_03;

// Interface thanh toán COD
public interface CODPayable extends PaymentMethod {

    void processCOD(double amount);

}

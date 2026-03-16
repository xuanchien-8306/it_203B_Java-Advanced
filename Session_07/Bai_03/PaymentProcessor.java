package Session_07.Bai_03;

// Lớp điều phối thanh toán
public class PaymentProcessor {

    // xử lý COD
    public void payCOD(CODPayable payment, double amount) {

        payment.processCOD(amount);

    }

    // xử lý thẻ tín dụng
    public void payCard(CardPayable payment, double amount) {

        payment.processCreditCard(amount);

    }

    // xử lý ví điện tử
    public void payEWallet(EWalletPayable payment, double amount) {

        payment.processEWallet(amount);

    }
}

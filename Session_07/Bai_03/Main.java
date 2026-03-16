package Session_07.Bai_03;

public class Main {

    public static void main(String[] args) {

        PaymentProcessor processor = new PaymentProcessor();

        // ===== COD =====
        System.out.println("Input: Thanh toán COD - 500000");
        CODPayable cod = new CODPayment();
        processor.payCOD(cod, 500000);


        // ===== Credit Card =====
        System.out.println("Input: Thanh toán thẻ tín dụng - 1000000");
        CardPayable card = new CreditCardPayment();
        processor.payCard(card, 1000000);


        // ===== Momo =====
        System.out.println("Input: Thanh toán ví MoMo - 750000");
        EWalletPayable momo = new MomoPayment();
        processor.payEWallet(momo, 750000);


        // ===== Kiểm tra LSP =====
        System.out.println("Kiểm tra LSP: thay thế implementation");

        EWalletPayable wallet;

        wallet = new MomoPayment(); // thay thế implementation
        processor.payEWallet(wallet, 300000);
    }
}
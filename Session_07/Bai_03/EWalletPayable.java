package Session_07.Bai_03;

// Interface cho ví điện tử
public interface EWalletPayable extends PaymentMethod {

    void processEWallet(double amount);

}

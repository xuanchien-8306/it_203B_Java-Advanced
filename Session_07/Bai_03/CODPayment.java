package Session_07.Bai_03;

// Lớp xử lý thanh toán COD
public class CODPayment implements CODPayable {

    @Override
    public void processCOD(double amount) {

        System.out.println("Xử lý thanh toán COD: " + amount + " - Thành công");

    }
}

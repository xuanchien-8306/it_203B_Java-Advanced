package Session_14.HN_K24_CNTT5_TaXuanChien;

public class Main {
    public static void main(String[] args) {
        System.out.println("Case 1: Chuyển thành công");
        Service.transfer("ACC01", "ACC02", 1000);

        System.out.println("\nCase 2: Không đủ tiền");
        Service.transfer("ACC01", "ACC02", 100000);

        System.out.println("\nCase 3: Tài khoản không tồn tại");
        Service.transfer("ACC99", "ACC02", 1000);

        System.out.println("\nCase 4: Tai khoản nhận không tồn tại");
        Service.transfer("ACC01", "ACC99", 1000);

        System.out.println("\nCase 5: S tiền âm");
        Service.transfer("ACC01", "ACC02", -500);
    }
}
package presentation;

import business.MenuBusiness;
import business.OrderBusiness;
import business.TableBusiness;
import entity.User;
import utils.InputUtils;

public class CustomerMenu {
    private MenuBusiness menuBusiness = new MenuBusiness();
    private OrderBusiness orderBusiness = new OrderBusiness();
    private TableBusiness tableBusiness = new TableBusiness();

    public void showMenu(User customer) {
        while (true) {
            System.out.println("\n╭─────────────────────────────────────────────────╮");
            System.out.println("│            MENU DÀNH CHO KHÁCH HÀNG             │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.printf ("│  Xin chào: %-35s │\n", customer.getFullName());
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│  [1] Xem thực đơn                               │");
            System.out.println("│  [2] Chọn bàn & Gọi món                         │");
            System.out.println("│  [3] Theo dõi món đã gọi                        │");
            System.out.println("│  [4] Hủy món (Chỉ khi PENDING)                  │");
            System.out.println("│  [0] Đăng xuất                                  │");
            System.out.println("╰─────────────────────────────────────────────────╯");

            String choice = InputUtils.readLine("=> Vui lòng chọn: ");
            if (choice.equals("0")) break;

            switch (choice) {
                case "1" -> menuBusiness.displayMenu(null); // Hiển thị toàn bộ
                case "2" -> {
                    System.out.println("\n--- CHỌN BÀN TRỐNG ---");
                    tableBusiness.displayAllTables(); // Bạn có thể viết thêm hàm chỉ hiển thị bàn AVAILABLE
                    int tableId = Integer.parseInt(InputUtils.readLine("Nhập ID Bàn bạn muốn ngồi: "));

                    System.out.println("\n--- CHỌN MÓN ---");
                    menuBusiness.displayMenu(null);
                    int menuItemId = Integer.parseInt(InputUtils.readLine("Nhập ID món ăn: "));
                    int qty = Integer.parseInt(InputUtils.readLine("Nhập số lượng: "));
                    double price = 50000; // Thực tế: Viết thêm hàm lấy giá trị price từ DB thông qua menuItemId

                    if (orderBusiness.placeOrder(customer.getId(), tableId, menuItemId, qty, price)) {
                        System.out.println("[+] Gọi món thành công! Vui lòng chờ bếp chuẩn bị.");
                    }
                }
                case "3" -> orderBusiness.viewMyOrders(customer.getId());
                case "4" -> {
                    orderBusiness.viewMyOrders(customer.getId());
                    int orderItemId = Integer.parseInt(InputUtils.readLine("Nhập ID chi tiết món muốn hủy: "));
                    if (orderBusiness.cancelOrderItem(orderItemId)) {
                        System.out.println("[+] Đã hủy món thành công!");
                    }
                }
            }
        }
    }
}
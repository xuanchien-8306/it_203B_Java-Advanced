package presentation;

import business.MenuBusiness;
import business.OrderBusiness;
import business.TableBusiness;
import entity.User;
import utils.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerMenu {
    private MenuBusiness menuBusiness = new MenuBusiness();
    private OrderBusiness orderBusiness = new OrderBusiness();
    private TableBusiness tableBusiness = new TableBusiness();

    public void showMenu(User customer) {
        while (true) {
            System.out.println("\n╭─────────────────────────────────────────────────╮");
            System.out.println("│            MENU DÀNH CHO KHÁCH HÀNG             │");
            System.out.printf ("│  Xin chào: %-35s  │\n", customer.getFullName());
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│  [1] Xem thực đơn nhà hàng                      │");
            System.out.println("│  [2] Chọn bàn & Gọi món                         │");
            System.out.println("│  [3] Theo dõi món đã gọi                        │");
            System.out.println("│  [4] Hủy món (Chỉ khi PENDING)                  │");
            System.out.println("│  [0] Đăng xuất                                  │");
            System.out.println("╰─────────────────────────────────────────────────╯");

            String choice = InputUtils.readLine(" => Vui lòng chọn chức năng: ").trim();
            if (choice.equals("0")) {
                System.out.println("Cảm ơn Quý khách! Đã đăng xuất.");
                break;
            }

            switch (choice) {
                case "1" -> {
                    System.out.println("\n--- THỰC ĐƠN NHÀ HÀNG ---");
                    menuBusiness.displayMenu(null);
                }
                case "2" -> orderFoodFlow(customer);
                case "3" -> {
                    System.out.println("\n--- LỊCH SỬ GỌI MÓN CỦA BẠN ---");
                    orderBusiness.viewMyOrders(customer.getId());
                }
                case "4" -> cancelFoodFlow(customer);
                default -> System.out.println("[!] Lựa chọn không hợp lệ.");
            }
        }
    }

    // gọi món hiển thị và nhập SỐ BÀN
    private void orderFoodFlow(User customer) {
        // Lấy danh sách SỐ BÀN đang dùng
        java.util.List<Integer> myActiveTableNumbers = orderBusiness.getActiveTableNumbers(customer.getId());
        int orderId = -1;
        int tableId = -1;
        int tableNo = -1; // SỐ BÀN do người dùng nhập

        if (!myActiveTableNumbers.isEmpty()) {
            System.out.println("\n[i] Bạn đang có đơn hàng chưa thanh toán tại SỐ BÀN: " + myActiveTableNumbers.toString());
            System.out.println("Bạn muốn làm gì?");
            System.out.println(" [1] Gọi thêm món cho bàn đang ngồi");
            System.out.println(" [2] Đặt thêm một bàn MỚI");

            String choice;
            while (true) {
                choice = utils.InputUtils.readLine("=> Chọn (1 hoặc 2): ").trim();
                if (choice.equals("1") || choice.equals("2")) break;
                System.out.println("[!] Vui lòng chỉ chọn 1 hoặc 2!");
            }

            if (choice.equals("1")) {
                while (true) {
                    String input = utils.InputUtils.readLine("Nhập SỐ BÀN bạn muốn gọi thêm món (hoặc 0 để hủy): ").trim();
                    try {
                        tableNo = Integer.parseInt(input);
                        if (tableNo == 0) return;

                        // Kiểm tra Số bàn nhập vào có nằm trong danh sách đang ngồi không
                        if (myActiveTableNumbers.contains(tableNo)) {
                            orderId = orderBusiness.getActiveOrderIdByTableNumber(customer.getId(), tableNo);
                            break;
                        } else {
                            System.out.println("[!] Lỗi: Số bàn này không thuộc về bạn hoặc đã thanh toán.");
                        }
                    } catch (Exception e) {
                        System.err.println("[!] Lỗi: Số bàn phải là số nguyên."); }
                }
            }
        }

        // khách chọn [2] (Mở bàn mới) HOẶC khách chưa có bàn nào
        if (orderId == -1) {
            System.out.println("\n--- BƯỚC 1: CHỌN BÀN MỚI ---");
            tableBusiness.displayAvailableTables();

            while (true) {
                String input = utils.InputUtils.readLine("Nhập SỐ BÀN bạn muốn chọn (hoặc 0 để hủy): ").trim();
                if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                try {
                    tableNo = Integer.parseInt(input);
                    if (tableNo == 0) return;

                    // Hệ thống tự động dịch Số Bàn thành ID Bàn ẩn bên dưới
                    tableId = tableBusiness.getAvailableTableIdByNumber(tableNo);

                    if (tableId == -1) {
                        System.out.println("[!] Lỗi: Bàn số " + tableNo + " không tồn tại hoặc đã có người ngồi! Vui lòng chọn Số bàn khác.");
                    } else {
                        break;
                    }
                } catch (Exception e) { System.out.println("[!] Lỗi: Số bàn phải là số nguyên!"); }
            }
        }

        System.out.println("\n--- BƯỚC 2: CHỌN MÓN ĂN/ĐỒ UỐNG ---");
        menuBusiness.displayMenu(null);

        java.util.List<Integer> listItems = new java.util.ArrayList<>();
        java.util.List<Integer> listQtys = new java.util.ArrayList<>();
        java.util.List<Double> listPrices = new java.util.ArrayList<>();

        while (true) {
            int menuItemId;
            while (true) {
                String input = utils.InputUtils.readLine("Nhập ID món muốn gọi: ").trim();
                if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                try {
                    menuItemId = Integer.parseInt(input);
                    if (!menuBusiness.isMenuItemExists(menuItemId)) {
                        System.out.println("[!] Lỗi: ID món không tồn tại! Vui lòng chọn lại.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("[!] Lỗi: ID món phải là số nguyên!"); }
            }

            int qty;
            while (true) {
                String input = utils.InputUtils.readLine("Nhập số lượng (> 0): ").trim();
                if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                try {
                    qty = Integer.parseInt(input);
                    if (qty <= 0) System.out.println("[!] Lỗi: Số lượng phải lớn hơn 0!");
                    else break;
                } catch (Exception e) {
                    System.err.println("[!] Lỗi: Số lượng phải là số nguyên!"); }
            }

            double price = menuBusiness.getMenuItemPrice(menuItemId);

            listItems.add(menuItemId);
            listQtys.add(qty);
            listPrices.add(price);

            String continueOrder = utils.InputUtils.readLine("=> Bạn có muốn gọi thêm món khác không? (y/n): ").trim();
            if (!continueOrder.equalsIgnoreCase("y")) {
                break;
            }
        }

        // Lưu vào CSDL
        if (orderId != -1) {
            // Khách gọi thêm món vào bàn cũ
            if (orderBusiness.addItemsToExistingOrder(orderId, listItems, listQtys, listPrices)) {
                System.out.println("\n[+] GỌI THÊM MÓN VÀO BÀN SỐ " + tableNo + " THÀNH CÔNG! Trạng thái: PENDING (Đang chờ bếp).");
            } else {
                System.out.println("\n[!] Lỗi hệ thống: Không thể gọi thêm lúc này.");
            }
        } else {
            // Khách mở bàn mới
            if (orderBusiness.placeOrderMultiple(customer.getId(), tableId, listItems, listQtys, listPrices)) {
                System.out.println("\n[+] ĐẶT MÓN VÀO BÀN SỐ " + tableNo + " THÀNH CÔNG! Trạng thái: PENDING (Đang chờ bếp).");
            } else {
                System.out.println("\n[!] Lỗi hệ thống: Không thể đặt bàn lúc này.");
            }
        }
    }

    // Hủy món
    private void cancelFoodFlow(User customer) {
        System.out.println("\n--- HỦY MÓN ĐÃ GỌI ---");
        orderBusiness.viewMyOrders(customer.getId());

        int orderItemId;
        while (true) {
            String input = InputUtils.readLine("Nhập [ID] của món muốn hủy (hoặc 0 để thoát): ").trim();
            if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
            try {
                orderItemId = Integer.parseInt(input);
                if (orderItemId == 0) return;
                break;
            } catch (Exception e) { System.out.println("[!] Lỗi: ID phải là số nguyên!"); }
        }

        String confirm = InputUtils.readLine("Xác nhận hủy món có ID " + orderItemId + "? (y/n): ").trim();
        if (confirm.equalsIgnoreCase("y")) {
            if (orderBusiness.cancelOrderItem(orderItemId, customer.getId())) {
                System.out.println("[+] Đã hủy món thành công!");
            }
        } else {
            System.out.println("Đã quay lại.");
        }
    }
}
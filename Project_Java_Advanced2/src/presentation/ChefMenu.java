package presentation;

import business.OrderBusiness;
import entity.User;
import utils.InputUtils;

public class ChefMenu {
    private OrderBusiness orderBusiness = new OrderBusiness();

    public void showMenu(User chef) {
        while (true) {
            System.out.println("\n╭─────────────────────────────────────────────────╮");
            System.out.println("│         GIAO DIỆN NHÀ BẾP - [CHEF MENU]         │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.printf ("│  Đầu bếp: %-37s │\n", chef.getFullName());
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│  [1] Xem danh sách món khách đang gọi           │");
            System.out.println("│  [2] Cập nhật trạng thái món ăn                 │");
            System.out.println("│  [0] Đăng xuất                                  │");
            System.out.println("╰─────────────────────────────────────────────────╯");

            String choice = InputUtils.readLine(" => Chọn chức năng: ");
            switch (choice) {
                case "1" -> orderBusiness.displayChefTasks();
                case "2" -> updateItemStatusFlow();
                case "0" -> {
                    System.out.println("Đã đăng xuất khỏi giao diện Bếp!");
                    return;
                }
                default -> System.err.println("[!] Lựa chọn không hợp lệ.");
            }
        }
    }

    // xử lý cập nhật trạng thái
    private void updateItemStatusFlow() {
        while (true) {
            System.out.println("\n--- CẬP NHẬT TRẠNG THÁI MÓN ĂN ---");
            orderBusiness.displayChefTasks();
            System.out.println("\n[Lưu ý] Quy trình chuẩn: PENDING -> COOKING -> READY -> SERVED");
            String input = InputUtils.readLine("Nhập ID món ăn cần cập nhật (Hoặc nhập '0' để quay lại): ");

            if (input.equals("0")) {
                break;
            }

            try {
                int id = Integer.parseInt(input);
                String currentStatus = orderBusiness.getOrderItemStatus(id);

                if (currentStatus == null) {
                    System.out.println("[!] Lỗi: Không tìm thấy ID món ăn này.");
                    continue;
                }

                if (currentStatus.equals("SERVED") || currentStatus.equals("CANCELLED")) {
                    System.out.println("[!] Món này đã phục vụ hoặc bị hủy, không thể cập nhật nữa.");
                    continue;
                }

                // Xác định trạng thái tiếp theo bắt buộc
                String nextStatus = "";
                switch (currentStatus) {
                    case "PENDING" -> nextStatus = "COOKING";
                    case "COOKING" -> nextStatus = "READY";
                    case "READY" -> nextStatus = "SERVED";
                }

                System.out.println("\nTrạng thái hiện tại: " + currentStatus);
                System.out.println("Chuyển sang trạng thái tiếp theo: [" + nextStatus + "] ?");
                System.out.println("[1] Đồng ý cập nhật  |  [0] Hủy thao tác (Quay lại)");
                String confirm = InputUtils.readLine("=> Chọn: ");

                if (confirm.equals("1")) {
                    if (orderBusiness.updateOrderStatus(id, nextStatus)) {
                        System.out.println("[+] Thành công! Món ăn đã chuyển sang: " + nextStatus);
                    } else {
                        System.out.println("[!] Cập nhật thất bại do lỗi hệ thống.");
                    }
                } else if (confirm.equals("0")) {
                    System.out.println("Đã hủy thao tác cập nhật.");
                } else {
                    System.out.println("[!] Lựa chọn không hợp lệ.");
                }

            } catch (NumberFormatException e) {
                System.err.println("[!] Vui lòng nhập ID là một chữ số.");
            }
        }
    }
}
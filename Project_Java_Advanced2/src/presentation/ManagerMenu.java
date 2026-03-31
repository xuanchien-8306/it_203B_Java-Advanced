package presentation;

import business.CategoryBusiness;
import business.TableBusiness;
import entity.Category;
import entity.User;
import utils.InputUtils;
import java.util.List;

public class ManagerMenu {
    private CategoryBusiness categoryBusiness = new CategoryBusiness();
    private TableBusiness tableBusiness = new TableBusiness();
    private business.MenuBusiness menuBusiness = new business.MenuBusiness();

    public void showMenu(User manager) {
        while (true) {
            System.out.println("\n╭─────────────────────────────────────────────────╮");
            System.out.println("│       HỆ THỐNG QUẢN LÝ NHÀ HÀNG - MANAGER       │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.printf ("│  Tài khoản: %-35s │\n", manager.getFullName());
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│  [1] Quản lý Phân loại món                      │");
            System.out.println("│  [2] Quản lý Thực đơn                           │");
            System.out.println("│  [3] Quản lý Bàn                                │");
            System.out.println("│  [4] Thanh toán & Hóa đơn                       │");
            System.out.println("│  [0] Đăng xuất                                  │");
            System.out.println("╰─────────────────────────────────────────────────╯");

            String choice = InputUtils.readLine(" => Chọn chức năng: ");
            switch (choice) {
                case "1" -> manageCategories();
                case "2" -> manageMenuItems();
                case "3" -> manageTables();
                case "4" -> checkoutFlow();
                case "0" -> {
                    System.out.println("Đã đăng xuất khỏi giao diện Quản lý!");
                    return;
                }
                default -> System.err.println("[!] Lựa chọn không hợp lệ.");
            }
        }
    }

    // ==========================================
    // 1. QUẢN LÝ CATEGORY
    // ==========================================
    private void manageCategories() {
        while (true) {
            List<Category> list = categoryBusiness.getAllCategories();
            System.out.println("\n--- DANH SÁCH PHÂN LOẠI MÓN ---");
            System.out.println("+------+--------------------------------+");
            System.out.printf("| %-4s | %-30s |\n", "ID", "Tên Phân Loại");
            System.out.println("+------+--------------------------------+");
            if (list.isEmpty()) {
                System.out.println("| Chưa có dữ liệu!                      |");
            } else {
                for (Category c : list) {
                    System.out.printf("| %-4d | %-30s |\n", c.getId(), c.getName());
                }
            }
            System.out.println("+------+--------------------------------+");
            System.out.println("[1] Thêm  |  [2] Sửa  |  [3] Xóa  |  [4] Tìm kiếm  |  [0] Quay lại");

            String opt = utils.InputUtils.readLine(" => Chọn thao tác: ");
            if (opt.equals("0")) break;

            try {
                switch (opt) {
                    case "1" -> {
                        System.out.println("\n--- THÊM PHÂN LOẠI MỚI ---");
                        String name;
                        while (true) {
                            name = utils.InputUtils.readLine("Nhập tên phân loại mới: ");
                            if (name.isEmpty()) {
                                System.out.println("[!] Lỗi: Tên phân loại không được để trống!");
                            } else if (categoryBusiness.isCategoryNameExists(name)) {
                                System.out.println("[!] Lỗi: Tên '" + name + "' đã tồn tại! Vui lòng nhập tên khác.");
                            } else {
                                break; // Nhập đúng, không trùng thì thoát vòng lặp
                            }
                        }

                        if (categoryBusiness.addCategory(name)) {
                            System.out.println("[+] Thêm thành công!");
                        } else {
                            System.out.println("[!] Lỗi hệ thống khi thêm.");
                        }
                    }
                    case "2" -> {
                        int id = Integer.parseInt(utils.InputUtils.readLine("Nhập ID cần sửa: "));
                        String newName;
                        while (true) {
                            newName = utils.InputUtils.readLine("Nhập tên mới: ");
                            if (newName.isEmpty()) {
                                System.out.println("[!] Lỗi: Tên không được để trống!");
                            } else if (categoryBusiness.isCategoryNameExists(newName)) {
                                System.out.println("[!] Lỗi: Tên '" + newName + "' đã tồn tại!");
                            } else {
                                break;
                            }
                        }

                        if (categoryBusiness.updateCategory(id, newName)) {
                            System.out.println("[+] Sửa thành công!");
                        } else {
                            System.out.println("[!] ID không tồn tại hoặc lỗi cập nhật.");
                        }
                    }
                    case "3" -> {
                        int id;
                        while (true) {
                            try {
                                String input = utils.InputUtils.readLine("Nhập ID cần xóa (hoặc nhập 0 để hủy): ").trim();
                                if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }

                                id = Integer.parseInt(input);
                                if (id == 0) break;

                                if (!categoryBusiness.isCategoryIdExists(id)) {
                                    System.out.println("[!] Lỗi: ID phân loại không tồn tại! Vui lòng nhập lại.");
                                } else if (categoryBusiness.isCategoryInUse(id)) {
                                    // Chặn ngay từ đầu nếu danh mục có chứa món ăn
                                    System.out.println("[!] Lỗi: Không thể xóa! Danh mục này đang chứa món ăn. Vui lòng xóa/chuyển các món ăn trước.");
                                    id = 0; // Ép về 0 để hủy thao tác xóa
                                    break;
                                } else {
                                    break;
                                }
                            } catch (Exception e) { System.out.println("[!] Lỗi: ID phải là số nguyên!"); }
                        }
                        if (id == 0) continue;

                        String confirm = utils.InputUtils.readLine("Bạn có chắc muốn xóa ID " + id + "? (y/n): ").trim();
                        if (confirm.equalsIgnoreCase("y")) {
                            if (categoryBusiness.deleteCategory(id)) {
                                System.out.println("[+] Xóa thành công!");
                            } else {
                                System.err.println("[!] Lỗi hệ thống khi xóa.");
                            }
                        } else {
                            System.out.println("Đã hủy thao tác xóa.");
                        }
                    }
                    case "4" -> {
                        System.out.println("\n--- TÌM KIẾM PHÂN LOẠI ---");
                        String keyword = utils.InputUtils.readLine("Nhập từ khóa tên phân loại: ");
                        List<Category> searchResult = categoryBusiness.searchCategories(keyword);

                        System.out.println("\n=> KẾT QUẢ TÌM KIẾM CHO: \"" + keyword + "\"");
                        System.out.println("+------+--------------------------------+");
                        System.out.printf("| %-4s | %-30s |\n", "ID", "Tên Phân Loại");
                        System.out.println("+------+--------------------------------+");
                        if (searchResult.isEmpty()) {
                            System.out.println("| Không tìm thấy kết quả nào!           |");
                        } else {
                            for (Category c : searchResult) {
                                System.out.printf("| %-4d | %-30s |\n", c.getId(), c.getName());
                            }
                        }
                        System.out.println("+------+--------------------------------+");
                        utils.InputUtils.readLine("Ấn Enter để tiếp tục...");
                    }
                    default -> System.out.println("[!] Thao tác không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Vui lòng nhập ID là một con số.");
            }
        }
    }

    // ==========================================
    // 3. QUẢN LÝ BÀN ĂN (TABLES)
    // ==========================================
    private void manageTables() {
        while (true) {
            System.out.println("\n╭─────────────────────────────────────────────────╮");
            System.out.println("│               QUẢN LÝ BÀN ĂN                    │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│  [1] Xem toàn bộ danh sách bàn                  │");
            System.out.println("│  [2] Tìm kiếm bàn ăn                            │");
            System.out.println("│  [3] Thêm bàn mới                               │");
            System.out.println("│  [4] Sửa thông tin bàn                          │");
            System.out.println("│  [5] Xóa bàn                                    │");
            System.out.println("│  [0] Quay lại menu chính                        │");
            System.out.println("╰─────────────────────────────────────────────────╯");

            String opt = utils.InputUtils.readLine(" => Chọn chức năng: ").trim();
            if (opt.equals("0")) break;

            switch (opt) {
                case "1" -> {
                    System.out.println("\n--- DANH SÁCH BÀN ĂN ---");
                    tableBusiness.displayAllTables();
                }

                case "2" -> {
                    System.out.println("\n--- TÌM KIẾM BÀN ĂN ---");
                    while (true) {
                        String keyword = utils.InputUtils.readLine("Nhập số bàn muốn tìm (hoặc '0' để hủy): ");

                        if (keyword.isEmpty()) {
                            continue;
                        }
                        if (keyword.equals("0")) break;
                        System.out.println("\n=> KẾT QUẢ TÌM KIẾM CHO SỐ BÀN: " + keyword);
                        break;
                    }
                }

                case "3" -> {
                    System.out.println("\n--- THÊM BÀN MỚI ---");
                    int tableNo;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập số bàn (> 0): ").trim();
                        if (input.isEmpty()) {
                            System.out.println("[!] Lỗi: Không được để trống!");
                            continue;
                        }
                        try {
                            tableNo = Integer.parseInt(input);
                            if (tableNo <= 0) {
                                System.out.println("[!] Lỗi: Số bàn phải lớn hơn 0!");
                            } else if (tableBusiness.isTableNumberExists(tableNo, -1)) {
                                System.out.println("[!] Lỗi: Bàn số " + tableNo + " đã tồn tại! Vui lòng nhập số khác.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Lỗi: Số bàn phải là một con số nguyên!");
                        }
                    }

                    int capacity;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập sức chứa (số người > 0): ").trim();
                        if (input.isEmpty()) {
                            System.out.println("[!] Lỗi: Không được để trống!");
                            continue;
                        }
                        try {
                            capacity = Integer.parseInt(input);
                            if (capacity <= 0) {
                                System.out.println("[!] Lỗi: Sức chứa phải lớn hơn 0!");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Lỗi: Sức chứa phải là một con số nguyên!");
                        }
                    }

                    if (tableBusiness.addTable(tableNo, capacity)) {
                        System.out.println("[+] Thêm bàn thành công!");
                    } else {
                        System.out.println("[!] Thêm thất bại do lỗi hệ thống.");
                    }
                }
                case "4" -> {
                    System.out.println("\n--- SỬA THÔNG TIN BÀN ---");
                    int id;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập ID bàn muốn sửa (hoặc 0 để hủy): ").trim();
                        if (input.isEmpty()) {
                            System.out.println("[!] Lỗi: Không được để trống!");
                            continue;
                        }
                        try {
                            id = Integer.parseInt(input);
                            if (id == 0) break;

                            if (!tableBusiness.isTableIdExists(id)) {
                                System.out.println("[!] Lỗi: ID bàn không tồn tại! Vui lòng nhập lại ID khác.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Lỗi: ID phải là số nguyên.");
                        }
                    }
                    if (id == 0) continue;

                    System.out.println("\n--- Nhập thông tin mới ---");
                    int newTableNo;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập số bàn mới (> 0): ").trim();
                        if (input.isEmpty()) {
                            System.out.println("[!] Lỗi: Không được để trống!");
                            continue;
                        }
                        try {
                            newTableNo = Integer.parseInt(input);
                            if (newTableNo <= 0) {
                                System.out.println("[!] Lỗi: Số bàn phải lớn hơn 0!");
                            } else if (tableBusiness.isTableNumberExists(newTableNo, id)) {
                                System.out.println("[!] Lỗi: Bàn số " + newTableNo + " đã tồn tại! Vui lòng nhập số khác.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Lỗi: Số bàn phải là một con số nguyên!");
                        }
                    }

                    int newCapacity;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập sức chứa mới (số người > 0): ").trim();
                        if (input.isEmpty()) {
                            System.out.println("[!] Lỗi: Không được để trống!");
                            continue;
                        }
                        try {
                            newCapacity = Integer.parseInt(input);
                            if (newCapacity <= 0) {
                                System.out.println("[!] Lỗi: Sức chứa phải lớn hơn 0!");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Lỗi: Sức chứa phải là một con số nguyên!");
                        }
                    }

                    if (tableBusiness.updateTable(id, newTableNo, newCapacity)) {
                        System.out.println("[+] Cập nhật bàn thành công!");
                    } else {
                        System.out.println("[!] Cập nhật thất bại.");
                    }
                }
                case "5" -> {
                    System.out.println("\n--- XÓA BÀN ĂN ---");
                    int id;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập ID bàn muốn xóa (hoặc 0 để hủy): ").trim();
                        if (input.isEmpty()) {
                            System.err.println("[!] Lỗi: Không được để trống!");
                            continue;
                        }
                        try {
                            id = Integer.parseInt(input);
                            if (id == 0) break;

                            if (!tableBusiness.isTableIdExists(id)) {
                                System.err.println("[!] Lỗi: ID bàn không tồn tại! Vui lòng nhập lại.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("[!] Lỗi: ID phải là một số nguyên.");
                        }
                    }
                    if (id == 0) continue;

                    String confirm = utils.InputUtils.readLine("Bạn có chắc chắn muốn xóa bàn này? (y/n): ").trim();
                    if (confirm.equalsIgnoreCase("y")) {
                        if (tableBusiness.deleteTable(id)) {
                            System.out.println("[+] Đã xóa bàn thành công!");
                        } else {
                            System.err.println("[!] Xóa thất bại! (Bàn này có thể đang được sử dụng hoặc dính lịch sử Order).");
                        }
                    } else {
                        System.out.println("Đã hủy thao tác xóa.");
                    }
                }
                default -> System.err.println("[!] Lựa chọn không hợp lệ.");
            }
        }
    }

    // ==========================================
    // 2. QUẢN LÝ THỰC ĐƠN (MENU ITEMS)
    // ==========================================
    private void manageMenuItems() {
        while (true) {
            System.out.println("\n╭─────────────────────────────────────────────────╮");
            System.out.println("│             QUẢN LÝ THỰC ĐƠN MÓN ĂN             │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│  [1] Xem toàn bộ danh sách món                  │");
            System.out.println("│  [2] Tìm kiếm món theo tên                      │");
            System.out.println("│  [3] Thêm món mới                               │");
            System.out.println("│  [4] Sửa thông tin món                          │");
            System.out.println("│  [5] Xóa món                                    │");
            System.out.println("│  [0] Quay lại menu chính                        │");
            System.out.println("╰─────────────────────────────────────────────────╯");

            String opt = utils.InputUtils.readLine(" => Chọn chức năng: ").trim();
            if (opt.equals("0")) break;

            switch (opt) {
                case "1" -> {
                    System.out.println("\n--- TẤT CẢ THỰC ĐƠN ---");
                    menuBusiness.displayMenu(null); // null để hiển thị toàn bộ
                }
                case "2" -> {
                    System.out.println("\n--- TÌM KIẾM MÓN ĂN ---");
                    while (true) {
                        String keyword = utils.InputUtils.readLine("Nhập tên món muốn tìm kiếm (hoặc 0 để hủy): ").trim();
                        if (keyword.equals("0")) break;

                        if (keyword.isEmpty()) {
                            System.out.println("[!] Lỗi: Vui lòng không để trống từ khóa!");
                        } else {
                            System.out.println("\n=> KẾT QUẢ TÌM KIẾM CHO: \"" + keyword + "\"");
                            menuBusiness.displayMenu(keyword);
                            break;
                        }
                    }
                }
                case "3" -> {
                    System.out.println("\n--- THÊM MÓN MỚI ---");
                    String name;
                    while (true) {
                        name = utils.InputUtils.readLine("Nhập tên món: ").trim();
                        if (name.isEmpty() || name.length() > 100) {
                            System.out.println("[!] Lỗi: Tên món không hợp lệ (1-100 ký tự)!");
                        } else if (menuBusiness.isMenuItemNameExists(name)) {
                            System.out.println("[!] Lỗi: Tên món '" + name + "' đã tồn tại! Vui lòng nhập tên khác.");
                        } else {
                            break;
                        }
                    }

                    double price;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập giá tiền (> 0): ").trim();
                        if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                        try {
                            price = Double.parseDouble(input);
                            if (price <= 0) System.out.println("[!] Lỗi: Giá tiền phải lớn hơn 0!");
                            else break;
                        } catch (Exception e) { System.out.println("[!] Lỗi: Vui lòng nhập đúng định dạng số!"); }
                    }

                    String type;
                    while (true) {
                        System.out.println("Chọn loại món: [1] Đồ ăn (FOOD)  |  [2] Đồ uống (DRINK)");
                        String typeChoice = utils.InputUtils.readLine("=> Chọn: ").trim();
                        if (typeChoice.equals("1")) { type = "FOOD"; break; }
                        else if (typeChoice.equals("2")) { type = "DRINK"; break; }
                        else System.out.println("[!] Lỗi: Vui lòng chỉ gõ phím 1 hoặc 2!");
                    }

                    int stock = 0;
                    if (type.equals("DRINK")) {
                        while (true) {
                            String input = utils.InputUtils.readLine("Nhập số lượng tồn kho (> 0): ").trim();
                            if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                            try {
                                stock = Integer.parseInt(input);
                                if (stock <= 0) System.out.println("[!] Lỗi: Số lượng phải lớn hơn 0!");
                                else break;
                            } catch (Exception e) { System.out.println("[!] Lỗi: Số lượng phải là số nguyên!"); }
                        }
                    }

                    int catId;
                    while (true) {
                        System.out.println("\n- Danh sách ID Phân loại hiện có -");
                        for (entity.Category c : categoryBusiness.getAllCategories()) {
                            System.out.println("ID: " + c.getId() + " - " + c.getName());
                        }
                        String input = utils.InputUtils.readLine("Nhập ID Phân loại (Category ID): ").trim();
                        if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                        try {
                            catId = Integer.parseInt(input);
                            if (categoryBusiness.isCategoryIdExists(catId)) {
                                break;
                            } else {
                                System.out.println("[!] Lỗi: ID Phân loại không tồn tại!");
                            }
                        } catch (Exception e) { System.out.println("[!] Lỗi: ID Phân loại phải là số nguyên!"); }
                    }

                    if (menuBusiness.addMenuItem(name, price, catId, type, stock)) {
                        System.out.println("\n[+] Thêm món '" + name + "' thành công!");
                    } else {
                        System.out.println("\n[!] Lỗi hệ thống khi thêm món.");
                    }
                }
                case "4" -> {
                    System.out.println("\n--- SỬA THÔNG TIN MÓN ---");
                    int id;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập ID món muốn sửa (hoặc 0 để hủy): ").trim();
                        if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                        try {
                            id = Integer.parseInt(input);
                            if (id == 0) break;
                            if (!menuBusiness.isMenuItemExists(id)) {
                                System.out.println("[!] Lỗi: ID món ăn không tồn tại hoặc đã bị xóa! Vui lòng nhập lại.");
                            } else {
                                break;
                            }
                        } catch (Exception e) { System.out.println("[!] Lỗi: ID phải là số nguyên."); }
                    }
                    if (id == 0) continue;

                    System.out.println("\n--- Nhập thông tin mới ---");
                    String newName;
                    while (true) {
                        newName = utils.InputUtils.readLine("Tên mới: ").trim();
                        if (newName.isEmpty() || newName.length() > 100) {
                            System.out.println("[!] Lỗi: Tên món không hợp lệ (1-100 ký tự)!");
                        } else if (menuBusiness.isMenuItemNameExists(newName)) {
                            System.out.println("[!] Lỗi: Tên món '" + newName + "' đã tồn tại!");
                        } else {
                            break;
                        }
                    }

                    double newPrice;
                    while (true) {
                        String input = utils.InputUtils.readLine("Giá mới (> 0): ").trim();
                        if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                        try {
                            newPrice = Double.parseDouble(input);
                            if (newPrice <= 0) System.out.println("[!] Lỗi: Giá tiền phải lớn hơn 0!");
                            else break;
                        } catch (Exception e) { System.out.println("[!] Lỗi: Vui lòng nhập đúng định dạng số!"); }
                    }

                    String newType;
                    while (true) {
                        System.out.println("Loại món mới: [1] Đồ ăn (FOOD)  |  [2] Đồ uống (DRINK)");
                        String typeChoice = utils.InputUtils.readLine("=> Chọn: ").trim();
                        if (typeChoice.equals("1")) { newType = "FOOD"; break; }
                        else if (typeChoice.equals("2")) { newType = "DRINK"; break; }
                        else System.out.println("[!] Lỗi: Vui lòng chỉ chọn 1 hoặc 2!");
                    }

                    int newStock = 0;
                    if (newType.equals("DRINK")) {
                        while (true) {
                            String input = utils.InputUtils.readLine("Số lượng tồn kho mới (> 0): ").trim();
                            if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                            try {
                                newStock = Integer.parseInt(input);
                                if (newStock <= 0) System.out.println("[!] Lỗi: Số lượng tồn kho phải lớn hơn 0!");
                                else break;
                            } catch (Exception e) { System.out.println("[!] Lỗi: Số lượng phải là số nguyên!"); }
                        }
                    }

                    int newCatId;
                    while (true) {
                        System.out.println("\n- Danh sách ID Phân loại hiện có -");
                        for (entity.Category c : categoryBusiness.getAllCategories()) {
                            System.out.println("ID: " + c.getId() + " - " + c.getName());
                        }
                        String input = utils.InputUtils.readLine("ID Phân loại mới: ").trim();
                        if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                        try {
                            newCatId = Integer.parseInt(input);
                            if (categoryBusiness.isCategoryIdExists(newCatId)) {
                                break;
                            } else {
                                System.out.println("[!] Lỗi: ID Phân loại không tồn tại!");
                            }
                        } catch (Exception e) { System.out.println("[!] Lỗi: ID Phân loại phải là số nguyên!"); }
                    }

                    if (menuBusiness.updateMenuItemFull(id, newName, newPrice, newCatId, newType, newStock)) {
                        System.out.println("\n[+] Cập nhật thông tin món ăn thành công!");
                    } else {
                        System.out.println("\n[!] Cập nhật thất bại (Lỗi hệ thống).");
                    }
                }
                case "5" -> {
                    System.out.println("\n--- XÓA MÓN ĂN ---");
                    int id;
                    while (true) {
                        String input = utils.InputUtils.readLine("Nhập ID món muốn xóa (hoặc 0 để hủy): ").trim();
                        if (input.isEmpty()) { System.out.println("[!] Không được để trống!"); continue; }
                        try {
                            id = Integer.parseInt(input);
                            if (id == 0) break;
                            if (!menuBusiness.isMenuItemExists(id)) {
                                System.out.println("[!] Lỗi: ID món ăn không tồn tại hoặc đã bị xóa!");
                            } else {
                                break;
                            }
                        } catch (Exception e) { System.out.println("[!] Lỗi: ID phải là một chữ số."); }
                    }
                    if (id == 0) continue;

                    String confirm = utils.InputUtils.readLine("Bạn có chắc chắn muốn xóa món này? (y/n): ").trim();
                    if (confirm.equalsIgnoreCase("y")) {
                        if (menuBusiness.deleteMenuItem(id)) {
                            System.out.println("[+] Đã xóa món thành công!");
                        } else {
                            System.out.println("[!] Lỗi hệ thống khi xóa.");
                        }
                    } else {
                        System.out.println("Đã hủy thao tác xóa.");
                    }
                }
                default -> System.out.println("[!] Lựa chọn không hợp lệ.");
            }
        }
    }

    // ==========================================
    // 4. THANH TOÁN & HÓA ĐƠN
    // ==========================================
    private void checkoutFlow() {
        System.out.println("\n--- THANH TOÁN (CHECK OUT) ---");

        business.OrderBusiness orderBus = new business.OrderBusiness();

        if (!orderBus.displayOccupiedTables()) {
            return; // Nếu không có bàn nào đang ăn thì tự thoát
        }

        int tableNumber;
        while (true) {
            String input = utils.InputUtils.readLine("\nNhập SỐ BÀN muốn thanh toán (hoặc 0 để quay lại): ");
            if (input.isEmpty()) continue; // Chống trôi phím Enter

            try {
                tableNumber = Integer.parseInt(input);
                if (tableNumber == 0) return; // Quay lại menu

                // Trích xuất thông tin Bill dựa trên SỐ BÀN
                double[] billInfo = orderBus.printBillAndGetTotal(tableNumber);
                if (billInfo == null) {
                    System.out.println("[!] Lỗi: Số bàn này không tồn tại hoặc không có hóa đơn nào đang chờ thanh toán!");
                } else {
                    int orderId = (int) billInfo[0];
                    double totalAmount = billInfo[1];

                    // Kiểm tra xem Đầu bếp đã làm xong hết chưa
                    if (!orderBus.isOrderFullyServed(orderId)) {
                        System.out.println("\n[!] TỪ CHỐI THANH TOÁN: Bàn này vẫn còn món chưa phục vụ xong (Đang chờ Bếp / Đang nấu)!");
                        System.out.println("=> Quản lý vui lòng giục Bếp hoàn thành, hoặc Hủy các món khách không ăn nữa trước khi tính tiền.");
                        break;
                    }

                    // Xác nhận thu tiền
                    String confirm = utils.InputUtils.readLine("\nXác nhận khách đã thanh toán số tiền trên? (y/n): ");
                    if (confirm.equalsIgnoreCase("y")) {
                        if (orderBus.processCheckout(tableNumber, orderId, totalAmount)) {
                            System.out.println("[+] THANH TOÁN THÀNH CÔNG! Bàn số " + tableNumber + " đã được dọn trống (AVAILABLE).");
                        } else {
                            System.out.println("[!] Lỗi hệ thống: Không thể thanh toán lúc này.");
                        }
                    } else {
                        System.out.println("Đã hủy thao tác thanh toán.");
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Lỗi: Số bàn phải là số nguyên.");
            }
        }
    }
}
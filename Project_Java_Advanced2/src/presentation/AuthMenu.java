package presentation;

import business.UserBusiness;
import entity.User;
import utils.InputUtils;

public class AuthMenu {
    private UserBusiness userBusiness = new UserBusiness();

    public void start() {
        while (true) {
            System.out.println("\n╭─────────────────────────────────────────────────╮");
            System.out.println("│            HỆ THỐNG QUẢN LÝ NHÀ HÀNG            │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│  [1] Đăng nhập vào hệ thống                     │");
            System.out.println("│  [2] Đăng ký tài khoản (Khách hàng)             │");
            System.out.println("│  [0] Thoát chương trình                         │");
            System.out.println("╰─────────────────────────────────────────────────╯");

            String choice = utils.InputUtils.readLine(" => Vui lòng chọn chức năng: ");

            switch (choice) {
                case "1" -> loginFlow();
                case "2" -> registerFlow();
                case "0" -> {
                    System.out.println("Đang thoát chương trình... Tạm biệt!");
                    System.exit(0);
                }
                default -> System.err.println("[!] Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private void loginFlow() {
        System.out.println("\n--- ĐĂNG NHẬP ---");
        while (true) {
            String user = InputUtils.readLine("Tài khoản: ");
            String pass = InputUtils.readPassword("Mật khẩu: ");

            User loggedInUser = userBusiness.login(user, pass);
            if (loggedInUser != null) {
                System.out.println("\n[+] Đăng nhập thành công! Xin chào " + loggedInUser.getFullName());
                routeByRole(loggedInUser);
                break;
            } else {
                System.out.println("[!] Sai tài khoản/mật khẩu hoặc tài khoản bị khóa. Vui lòng thử lại!\n");
            }
        }
    }

    private void registerFlow() {
        System.out.println("\n--- ĐĂNG KÝ TÀI KHOẢN ---");
        String username, password, fullName;

        while (true) {
            username = utils.InputUtils.readLine("Nhập tài khoản: ");
            if (username.isEmpty()) System.out.println("[!] Không được để trống.");
            else if (userBusiness.isUsernameExists(username)) System.out.println("[!] Tài khoản đã tồn tại.");
            else break;
        }

        while (true) {
            password = utils.InputUtils.readPassword("Nhập mật khẩu: ");
            if (password.isEmpty()) System.out.println("[!] Mật khẩu không được để trống.");
            else break;
        }

        while (true) {
            fullName = utils.InputUtils.readLine("Nhập họ tên: ");
            if (fullName.isEmpty()) System.out.println("[!] Họ tên không được để trống.");
            else break;
        }

        entity.User newUser = new entity.User(username, password, fullName, "CUSTOMER");
        if (userBusiness.register(newUser)) {
            System.out.println("\n[+] Đăng ký thành công!");
            routeByRole(newUser);
        } else {
            System.out.println("[!] Lỗi hệ thống khi đăng ký.");
        }
    }

    private void routeByRole(User user) {
        if (user.getRole() == null) {
            System.err.println("[!] Lỗi: Tài khoản này chưa được cấp quyền trong Database.");
            return;
        }

        String role = user.getRole().toUpperCase();

        switch (role) {
            case "MANAGER" -> new ManagerMenu().showMenu(user);
            case "CUSTOMER" -> new CustomerMenu().showMenu(user);
            case "CHEF" -> new ChefMenu().showMenu(user);
            default -> System.err.println("[!] Hệ thống không nhận diện được quyền: " + role);
        }
    }
}
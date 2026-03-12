package Session_05.HN_K24_CNTT5_TaXuanChien;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ProductManager manager = new ProductManager();

        while (true) {

            System.out.println("========= HỆ THỐNG QUẢN LÝ SẢN PHẨM =========");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Hiển thị danh sách sản phẩm");
            System.out.println("3. Cập nhật số lượng theo ID");
            System.out.println("4. Xóa sản phẩm đã hết hàng");
            System.out.println("5. Thoát chương trình");
            System.out.println("==============================================");

            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Nhập ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Nhập tên sản phẩm: ");
                        String name = sc.nextLine();

                        System.out.print("Nhập giá: ");
                        double price = sc.nextDouble();

                        System.out.print("Nhập số lượng: ");
                        int quantity = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Nhập danh mục: ");
                        String category = sc.nextLine();

                        Product product = new Product(id, name, price, quantity, category);
                        manager.addProduct(product);

                        System.out.println("Thêm sản phẩm thành công");
                        break;

                    case 2:
                        manager.displayProducts();
                        break;

                    case 3:
                        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
                        int updateId = sc.nextInt();

                        System.out.print("Nhập số lượng mới: ");
                        int newQuantity = sc.nextInt();

                        manager.updateQuantity(updateId, newQuantity);

                        System.out.println("Cập nhật thành công");
                        break;

                    case 4:
                        manager.deleteOutOfStock();
                        System.out.println("Đã xóa các sản phẩm hết hàng");
                        break;

                    case 5:
                        System.out.println("Thoát chương trình...");
                        System.exit(0);

                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                }
            } catch (InvalidProductException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }
}

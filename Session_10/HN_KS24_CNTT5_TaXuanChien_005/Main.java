package Session_10.HN_KS24_CNTT5_TaXuanChien_005;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DocumentBusiness documentBusiness = DocumentBusiness.getInstance();
        Document document;
        int choice;
        while (true) {
            System.out.println("************** QUẢN LÝ TÀI LIỆU SỐ **************");
            System.out.println("1. Hiển thị danh sách toàn bộ tài liệu");
            System.out.println("2. Thêm mới tài liệu");
            System.out.println("3. Cập nhật thông tin tài liệu");
            System.out.println("4. Xoá tài liệu theo mã tài liệu");
            System.out.println("5. Tìm kiếm tài liệu theo tên");
            System.out.println("6. Lọc danh sách tài liệu phổ biến ( Lượt tải >= 1000 )");
            System.out.println("7. Sắp xếp danh sách giảm dần theo lượt tải");
            System.out.println("8. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    documentBusiness.displayList();
                    break;
                case 2:
//                    documentBusiness.addDocument(sc);
                    break;
                case 3:
//                    documentBusiness.updateDocument(sc);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    System.out.println("Thoát chương trình");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }
}

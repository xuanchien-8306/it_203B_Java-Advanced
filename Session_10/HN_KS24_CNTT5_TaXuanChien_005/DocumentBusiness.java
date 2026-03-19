package Session_10.HN_KS24_CNTT5_TaXuanChien_005;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Lớp xử lý nghiệp vụ
public class DocumentBusiness {
    private static DocumentBusiness instance;
    // tạo list
    private final List<Document> list = new ArrayList<>();

    private DocumentBusiness() {};

    public static DocumentBusiness getInstance() {
        if (instance == null) {
            instance = new DocumentBusiness();
        };
        return instance;
    };

    // hiển thị danh sách
    public void displayList() {
        if (list.isEmpty()) {
            System.out.println("Rỗng");
            return;
        }
        System.out.printf("%-10s | %-20s | %-10.2f | %-20d \n", "ID", "Name", "File size", "Downloads");
        list.forEach(Document::displayData);
    }

    // Thêm tài liệu
//    public boolean addDocument(Scanner d) {
//        boolean exists = list.stream()
//                .anyMatch(x -> x.getDocumentId().equalsIgnoreCase(d.getDocumentId()));
//
//        if (exists) {
//            System.out.println("Trùng mã giao dịch!");
//            return false;
//        }
//
//        list.add(d);
//        return true;
//    }

    // cập nhật thông tin
//    public void updateDocument(Scanner document) {
//        list.remove(document);
//        list.add(document);
//    }

    // Tìm kiếm theo tên
    public List<Document> getDocuments() {
        return list;
    }

    // xoá tài liệu
    public void deleteDocument(Document document) {
        list.remove(document);
    }

    // sắp xếp
    public void sortDocuments() {

    }
}

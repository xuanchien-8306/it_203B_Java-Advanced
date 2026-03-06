package Session_01;

import java.util.Scanner;

public class Bai_01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            System.out.printf("Nhập năm sinh của bạn: ");
            String namSinhStr = sc.nextLine();

            int namSinh = Integer.parseInt(namSinhStr);
            int tuoi = 2026 - namSinh;
            System.out.println("Tuổi của bạn là: " + tuoi);
        } catch (NumberFormatException  e){
            System.err.println("Lỗi: bạn phải nhập số ( ví dụ: 2006 )");
        } finally{
            sc.close();
            System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
        }
    }
}

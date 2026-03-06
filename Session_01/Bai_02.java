package Session_01;

import java.util.Scanner;

public class Bai_02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.printf("Nhập tổng số người dùng: ");
        int soNguoiDung = sc.nextInt();
        System.out.printf("Nhập số nhóm: ");
        int soNhom = sc.nextInt();

        try {
            int soNguoi = soNguoiDung / soNhom;
            System.out.println(soNguoi);
        } catch (ArithmeticException e){
            System.err.println("Không thể chia cho 0!");
        }

        System.out.println("Tiếp tục chạy");
    }
}

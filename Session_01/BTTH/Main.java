package Session_01.BTTH;

import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nhập tên: ");
            String name = sc.nextLine();

            System.out.print("Nhập tuổi: ");
            String age = sc.nextLine();

            System.out.print("Nhập email: ");
            String email = sc.nextLine();

            // đăng ký user
            String userData = UserService.registerUser(name, age, email);

            // lưu file
            UserService.saveUserToFile(userData);

        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());

        } catch (InvalidEmailException e) {
            System.out.println(e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Tuổi phải là một con số!");

        } catch (FileNotFoundException e) {
            System.out.println("Lỗi hệ thống: " + e.getMessage());

        } finally {
            System.out.println("Hoàn tất luồng xử lý đăng ký.");
            sc.close();
        }
    }
}
package utils;

import java.util.Scanner;

public class InputUtils {

    public static final Scanner sc = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static String readPassword(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("[!] Lỗi: Vui lòng nhập số hợp lệ!");
            }
        }
    }
}
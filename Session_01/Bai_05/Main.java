package Session_01.Bai_05;

import Session_01.Bai_03.User;

public class Main {
    public static void main(String[] args) {

        User user = new User();

        try {
            user.setAge(-3);
            System.out.println("Tuổi: " + user.getAge());

        } catch (InvalidAgeException e) {
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}

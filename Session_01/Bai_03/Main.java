package Session_01.Bai_03;

import Session_01.Bai_05.InvalidAgeException;

public class Main {
    public static void main(String[] args) {

        User user = new User();

        try {
            user.setAge(-5);
            System.out.println("Tuổi: " + user.getAge());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InvalidAgeException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}
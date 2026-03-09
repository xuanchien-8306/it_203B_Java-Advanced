package Session_02.Bai_02;

public class Main {
    public static void main(String[] args) {

        PasswordValidator validator = password -> password.length() >= 8;

        System.out.println("12345678 -> " + validator.validate("12345678"));
        System.out.println("1234 -> " + validator.validate("1234"));
    }
}

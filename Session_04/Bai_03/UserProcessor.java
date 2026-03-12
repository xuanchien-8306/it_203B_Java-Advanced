package Session_04.Bai_03;

public class UserProcessor {

    public String processEmail(String email) {

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        String[] parts = email.split("@");

        // kiểm tra tên miền
        if (parts.length != 2 || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Invalid email domain");
        }

        // chuẩn hóa lowercase
        return email.toLowerCase();
    }
}

package Session_04.Bai_01;

public class UserValidator {

    public boolean isValidUsername(String username) {

        if (username == null) {
            return false;
        }

        // độ dài 6-20 và không chứa khoảng trắng
        return username.length() >= 6 &&
                username.length() <= 20 &&
                !username.contains(" ");
    }
}

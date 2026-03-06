package Session_01.BTTH;
import java.io.FileNotFoundException;

public class UserService {

    // kiểm tra dữ liệu người dùng
    public static String registerUser(String name, String ageInput, String email)
            throws InvalidAgeException, InvalidEmailException {

        int age;

        try {
            age = Integer.parseInt(ageInput);
        } catch (NumberFormatException e) {
            throw e;
        }

        // kiểm tra tuổi
        if (age < 18) {
            throw new InvalidAgeException("Lỗi nghiệp vụ: Tuổi không đủ để đăng ký hệ thống.");
        }

        // kiểm tra email
        if (!email.contains("@")) {
            throw new InvalidEmailException("Lỗi nghiệp vụ: Email không hợp lệ.");
        }

        System.out.println("Đăng ký người dùng thành công!");

        return name + " - " + age + " - " + email;
    }

    // giả lập lưu file
    public static void saveUserToFile(String userData) throws FileNotFoundException {
        System.out.println("Đang lưu dữ liệu: " + userData);

        // giả lập lỗi hệ thống
        throw new FileNotFoundException("Không tìm thấy file lưu trữ.");
    }
}
package Session_01.Bai_05;

public class InvalidAgeException extends Exception {

    public InvalidAgeException(String msg) {
        super(msg);   // truyền thông báo lỗi lên lớp cha
    }
}
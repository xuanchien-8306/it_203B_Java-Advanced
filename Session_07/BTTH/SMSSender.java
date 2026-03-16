package Session_07.BTTH;

public class SMSSender implements Notification {

    @Override
    public void send(String email, String message) {

        System.out.println("Đã gửi SMS tới: " + email);
    }
}

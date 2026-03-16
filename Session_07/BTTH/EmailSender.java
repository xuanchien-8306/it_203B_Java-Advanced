package Session_07.BTTH;

public class EmailSender implements Notification {

    @Override
    public void send(String email, String message) {

        System.out.println("Đã gửi Email tới: " + email);
    }
}

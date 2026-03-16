package Session_07.Bai_04;

// Implementation gửi thông báo qua SMS
public class SMSNotification implements NotificationService {

    @Override
    public void send(String message, String recipient) {

        System.out.println("Gửi SMS: " + message);

    }
}
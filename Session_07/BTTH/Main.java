package Session_07.BTTH;

public class Main {

    public static void main(String[] args) {

        Order order = new Order("customer@gmail.com");

        Database db = new Database();
        Notification notification = new EmailSender();

        OrderProcessor processor =
                new OrderProcessor(db, notification);

        PaymentMethod payment = new CreditCard();

        processor.processOrder(order, payment);
    }
}

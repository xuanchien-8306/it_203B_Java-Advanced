package Session_06.Bai_04;

public class Main {

    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);

        Thread counter1 =
                new Thread(new BookingCounter("Quầy 1", roomA, roomB));

        Thread counter2 =
                new Thread(new BookingCounter("Quầy 2", roomA, roomB));

        Thread supplier =
                new Thread(new TicketSupplier(roomA, 3, 3000));

        counter1.start();
        counter2.start();
        supplier.start();
    }
}

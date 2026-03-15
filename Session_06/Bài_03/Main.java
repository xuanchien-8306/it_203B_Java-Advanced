package Session_06.Bài_03;

public class Main {

    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A",5);
        TicketPool roomB = new TicketPool("B",5);

        BookingCounter counter1 =
                new BookingCounter("Quầy 1", roomA, roomB);

        BookingCounter counter2 =
                new BookingCounter("Quầy 2", roomB, roomA);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();
    }
}

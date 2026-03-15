package Session_06.Bài_02;

import java.util.Random;

public class BookingCounter implements Runnable {

    private String counterName;
    private TicketPool roomA;
    private TicketPool roomB;
    private int soldCount = 0;

    private Random random = new Random();

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    @Override
    public void run() {

        while (true) {

            if (roomA.remainingTickets() == 0 && roomB.remainingTickets() == 0) {
                break;
            }

            Ticket ticket;

            if (random.nextBoolean()) {
                ticket = roomA.sellTicket();
            } else {
                ticket = roomB.sellTicket();
            }

            if (ticket != null) {

                soldCount++;

                System.out.println(counterName +
                        " đã bán vé " +
                        ticket.getTicketId());
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(counterName + " bán được: " + soldCount + " vé");
    }
}

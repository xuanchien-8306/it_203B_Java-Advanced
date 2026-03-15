package Session_06.Bai_04;

public class BookingCounter implements Runnable {

    private String counterName;
    private TicketPool roomA;
    private TicketPool roomB;

    public BookingCounter(String counterName,
                          TicketPool roomA,
                          TicketPool roomB) {

        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    @Override
    public void run() {

        while (true) {

            // bán vé phòng A
            Ticket ticketA = roomA.sellTicket(counterName);

            if (ticketA != null) {
                System.out.println(counterName +
                        " bán vé " + ticketA.getTicketId());
            }

            // bán vé phòng B
            Ticket ticketB = roomB.sellTicket(counterName);

            if (ticketB != null) {
                System.out.println(counterName +
                        " bán vé " + ticketB.getTicketId());
            }

            try {
                Thread.sleep(500); // mô phỏng thao tác bán vé
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

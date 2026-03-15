package Session_06.Bài_03;

public class BookingCounter implements Runnable {

    private String counterName;
    private TicketPool firstPool;
    private TicketPool secondPool;

    public BookingCounter(String counterName,
                          TicketPool firstPool,
                          TicketPool secondPool) {

        this.counterName = counterName;
        this.firstPool = firstPool;
        this.secondPool = secondPool;
    }

    @Override
    public void run() {

        while (true) {

            if (firstPool.remainingTickets() == 0 ||
                    secondPool.remainingTickets() == 0) {

                System.out.println(counterName +
                        ": Hết vé một trong hai phòng, bán combo thất bại");
                break;
            }

            sellCombo();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sellCombo() {

        TicketPool first;
        TicketPool second;

        if (firstPool.getRoomName().compareTo(secondPool.getRoomName()) < 0) {
            first = firstPool;
            second = secondPool;
        } else {
            first = secondPool;
            second = firstPool;
        }

        synchronized (first) {
            synchronized (second) {

                Ticket t1 = first.getAvailableTicket();
                Ticket t2 = second.getAvailableTicket();

                if (t1 != null && t2 != null) {

                    t1.setSold(true);
                    t2.setSold(true);

                    System.out.println(counterName +
                            " bán combo thành công: "
                            + t1.getTicketId()
                            + " & "
                            + t2.getTicketId());
                } else {

                    System.out.println(counterName +
                            ": Không đủ vé để bán combo");
                }
            }
        }
    }
}

package Session_06.Bai_04;

public class TicketSupplier implements Runnable {

    private TicketPool roomA;
    private int supplyCount;
    private int interval;

    public TicketSupplier(TicketPool roomA,
                          int supplyCount,
                          int interval) {

        this.roomA = roomA;
        this.supplyCount = supplyCount;
        this.interval = interval;
    }

    @Override
    public void run() {

        while (true) {

            try {
                Thread.sleep(interval); // chờ trước khi cung cấp vé mới
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // thêm vé và đánh thức các quầy
            roomA.addTickets(supplyCount);
        }
    }
}
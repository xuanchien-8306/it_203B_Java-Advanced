package Session_06.Bai_01;

public class BoxOffice implements Runnable {

    private String name;
    private TicketPool pool;
    private int sold = 0;

    public BoxOffice(String name, TicketPool pool) {
        this.name = name;
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true) {

            Ticket ticket = pool.sellTicket(name);

            if (ticket != null) {
                sold++;
            } else {
                if (pool.isSupplierFinished()) {
                    break;
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(name + " bán được: " + sold + " vé");
    }
}
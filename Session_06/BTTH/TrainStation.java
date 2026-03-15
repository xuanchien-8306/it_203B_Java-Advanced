package Session_06.BTTH;

public class TrainStation {

    private int tickets = 10;

    public synchronized void sellTicket(String officeName) {

        if (tickets > 0) {
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tickets--;

            System.out.println("[" + officeName + "] đã bán 1 vé. Số vé còn lại: " + tickets);

        } else {
            System.out.println("[" + officeName + "] thông báo: Đã hết vé!");
        }
    }

    public synchronized boolean hasTickets() {
        return tickets > 0;
    }
}

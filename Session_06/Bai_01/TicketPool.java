package Session_06.Bai_01;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {

    private String roomName;
    private Queue<Ticket> tickets = new LinkedList<>();
    private int counter = 1;
    private boolean supplierFinished = false;

    public TicketPool(String roomName, int initial) {
        this.roomName = roomName;
        addTickets(initial);
    }

    public synchronized Ticket sellTicket(String counterName) {
        if (!tickets.isEmpty()) {
            Ticket t = tickets.poll();
            System.out.println(counterName + " đã bán vé " + t.getId());
            return t;
        }
        return null;
    }

    public synchronized void addTickets(int count) {
        for (int i = 0; i < count; i++) {
            String id = roomName + "-" + String.format("%03d", counter++);
            tickets.add(new Ticket(id));
        }
        System.out.println("Nhà cung cấp: Đã thêm " + count + " vé vào phòng " + roomName);
    }

    public synchronized int remaining() {
        return tickets.size();
    }

    public synchronized boolean isSupplierFinished() {
        return supplierFinished;
    }

    public synchronized void setSupplierFinished(boolean finished) {
        this.supplierFinished = finished;
    }

    public String getRoomName() {
        return roomName;
    }
}
package Session_06.Bai_04;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {

    private String roomName;
    private List<Ticket> tickets = new ArrayList<>();
    private int counter = 1;

    public TicketPool(String roomName, int initial) {

        this.roomName = roomName;

        // tạo vé ban đầu
        for (int i = 0; i < initial; i++) {
            String id = roomName + "-" + String.format("%03d", counter++);
            tickets.add(new Ticket(id, roomName));
        }
    }

    // bán 1 vé
    public synchronized Ticket sellTicket(String counterName) {

        // nếu hết vé thì chờ
        while (remainingTickets() == 0) {
            try {
                System.out.println(counterName +
                        ": Hết vé phòng " + roomName + ", đang chờ...");
                wait(); // thread ngủ cho đến khi có notify
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // tìm vé chưa bán
        for (Ticket t : tickets) {
            if (!t.isSold()) {
                t.setSold(true);
                return t;
            }
        }

        return null;
    }

    // thêm vé mới vào hệ thống
    public synchronized void addTickets(int count) {

        for (int i = 0; i < count; i++) {

            String id = roomName + "-" + String.format("%03d", counter++);
            tickets.add(new Ticket(id, roomName));
        }

        System.out.println("Nhà cung cấp: Đã thêm "
                + count + " vé vào phòng " + roomName);

        // đánh thức tất cả thread đang chờ
        notifyAll();
    }

    // đếm vé còn lại
    public int remainingTickets() {

        int count = 0;

        for (Ticket t : tickets) {
            if (!t.isSold()) {
                count++;
            }
        }

        return count;
    }
}

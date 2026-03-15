package Session_06.Bài_02;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {

    private String roomName;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketPool(String roomName, int total) {

        this.roomName = roomName;

        for (int i = 1; i <= total; i++) {
            String id = roomName + "-" + String.format("%03d", i);
            tickets.add(new Ticket(id, roomName));
        }
    }

    public synchronized Ticket sellTicket() {

        for (Ticket t : tickets) {

            if (!t.isSold()) {
                t.setSold(true);
                return t;
            }
        }

        return null;
    }

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
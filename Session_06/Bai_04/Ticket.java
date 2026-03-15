package Session_06.Bai_04;

public class Ticket {

    private String ticketId;   // mã vé (A-001, B-002...)
    private String roomName;   // tên phòng
    private boolean isSold;    // trạng thái đã bán

    public Ticket(String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isSold = false;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getRoomName() {
        return roomName;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }
}

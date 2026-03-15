package Session_06.Bai_01;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        Thread counter1 = new Thread(new BoxOffice("Quầy 1", roomA));
        Thread counter2 = new Thread(new BoxOffice("Quầy 2", roomB));

        Thread supplier = new Thread(
                new TicketSupplier(roomA, roomB, 3, 3000, 3)
        );

        counter1.start();
        counter2.start();
        supplier.start();

        counter1.join();
        counter2.join();
        supplier.join();

        System.out.println("Vé còn lại phòng A: " + roomA.remaining());
        System.out.println("Vé còn lại phòng B: " + roomB.remaining());
        System.out.println("Kết thúc chương trình");
    }
}

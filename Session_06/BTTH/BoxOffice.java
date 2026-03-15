package Session_06.BTTH;

public class BoxOffice implements Runnable {

    private TrainStation station;
    private String officeName;

    public BoxOffice(TrainStation station, String officeName) {
        this.station = station;
        this.officeName = officeName;
    }

    @Override
    public void run() {

        while (station.hasTickets()) {

            station.sellTicket(officeName);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
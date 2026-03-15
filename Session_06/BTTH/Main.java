package Session_06.BTTH;

public class Main {

    public static void main(String[] args) {

        TrainStation station = new TrainStation();

        Thread office1 = new Thread(new BoxOffice(station, "Quầy 1"));
        Thread office2 = new Thread(new BoxOffice(station, "Quầy 2"));
        Thread office3 = new Thread(new BoxOffice(station, "Quầy 3"));

        office1.start();
        office2.start();
        office3.start();
    }
}
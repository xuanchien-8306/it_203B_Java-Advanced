package Session_08.Bai_01;

// Thiết bị Quạt
public class Fan implements Device {

    @Override
    public void turnOn() {
        System.out.println("Quạt: Đang quay.");
    }

    @Override
    public void turnOff() {
        System.out.println("Quạt: Tắt.");
    }
}

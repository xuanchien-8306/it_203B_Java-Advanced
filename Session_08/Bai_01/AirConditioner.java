package Session_08.Bai_01;

// Thiết bị Điều hòa
public class AirConditioner implements Device {

    @Override
    public void turnOn() {
        System.out.println("Điều hòa: Bật làm mát.");
    }

    @Override
    public void turnOff() {
        System.out.println("Điều hòa: Tắt.");
    }
}
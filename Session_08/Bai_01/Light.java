package Session_08.Bai_01;

// Thiết bị Đèn
public class Light implements Device {

    @Override
    public void turnOn() {
        System.out.println("Đèn: Bật sáng.");
    }

    @Override
    public void turnOff() {
        System.out.println("Đèn: Tắt.");
    }
}
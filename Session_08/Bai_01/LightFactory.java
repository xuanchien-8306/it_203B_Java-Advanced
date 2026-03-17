package Session_08.Bai_01;

public class LightFactory extends DeviceFactory {

    @Override
    public Device createDevice() {

        System.out.println("LightFactory: Đã tạo đèn mới.");
        return new Light();
    }
}
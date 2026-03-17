package Session_08.Bai_01;

public class FanFactory extends DeviceFactory {

    @Override
    public Device createDevice() {

        System.out.println("FanFactory: Đã tạo quạt mới.");
        return new Fan();
    }
}

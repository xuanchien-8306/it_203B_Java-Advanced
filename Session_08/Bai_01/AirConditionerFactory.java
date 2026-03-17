package Session_08.Bai_01;

public class AirConditionerFactory extends DeviceFactory {

    @Override
    public Device createDevice() {

        System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
        return new AirConditioner();
    }
}

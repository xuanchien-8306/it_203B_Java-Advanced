package Session_08.Bai_02;

public class ThermometerAdapter implements TemperatureSensor {

    private OldThermometer oldThermometer;

    public ThermometerAdapter(OldThermometer oldThermometer) {
        this.oldThermometer = oldThermometer;
        // Nhận cảm biến cũ vào để "bọc lại"
    }

    @Override
    public double getTemperatureCelsius() {
        int f = oldThermometer.getTemperatureFahrenheit();
        // Lấy dữ liệu từ cảm biến cũ (độ F)

        double c = (f - 32) * 5.0 / 9;
        // Chuyển đổi F → C

        return c;
    }
}

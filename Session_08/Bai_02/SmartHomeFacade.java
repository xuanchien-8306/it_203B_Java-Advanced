package Session_08.Bai_02;

public class SmartHomeFacade {

    private Light light;
    private Fan fan;
    private AC ac;
    private TemperatureSensor sensor;

    public SmartHomeFacade(Light light, Fan fan, AC ac, TemperatureSensor sensor) {
        this.light = light;
        this.fan = fan;
        this.ac = ac;
        this.sensor = sensor;
        // Inject toàn bộ subsystem vào facade
    }

    public void leaveHome() {
        // 1 lệnh nhưng gọi nhiều hệ thống bên dưới
        light.off();
        fan.off();
        ac.off();
    }

    public void sleepMode() {
        light.off();
        // Tắt đèn khi ngủ

        ac.setTemperature(28);
        // Set điều hòa 28°C

        fan.low();
        // Quạt chạy nhẹ
    }

    public void getCurrentTemperature() {
        double temp = sensor.getTemperatureCelsius();
        // Dùng adapter (nếu là cảm biến cũ)

        System.out.printf("Nhiệt độ hiện tại: %.1f°C\n", temp);
    }
}

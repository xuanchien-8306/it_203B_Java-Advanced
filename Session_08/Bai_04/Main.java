package Session_08.Bai_04;

public class Main {
    public static void main(String[] args) {

        TemperatureSensor sensor = new TemperatureSensor(); // tạo cảm biến

        Fan fan = new Fan(); // tạo quạt
        Humidifier humidifier = new Humidifier(); // tạo máy tạo ẩm

        // đăng ký observer
        sensor.attach(fan);
        sensor.attach(humidifier);

        // giả lập thay đổi nhiệt độ
        sensor.setTemperature(18);
        sensor.setTemperature(26);
    }
}
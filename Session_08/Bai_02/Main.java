package Session_08.Bai_02;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Tạo thiết bị
        Light light = new Light();
        Fan fan = new Fan();
        AC ac = new AC();

        // Cảm biến cũ → adapter
        OldThermometer oldThermometer = new OldThermometer();
        TemperatureSensor sensor = new ThermometerAdapter(oldThermometer);

        // Facade
        SmartHomeFacade facade = new SmartHomeFacade(light, fan, ac, sensor);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Xem nhiệt độ");
            System.out.println("2. Rời nhà");
            System.out.println("3. Chế độ ngủ");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    facade.getCurrentTemperature();
                    break;
                case 2:
                    facade.leaveHome();
                    break;
                case 3:
                    facade.sleepMode();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }
}
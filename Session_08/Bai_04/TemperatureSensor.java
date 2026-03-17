package Session_08.Bai_04;

import java.util.ArrayList;
import java.util.List;

class TemperatureSensor implements Subject {
    private List<Observer> observers = new ArrayList<>(); // danh sách observer
    private int temperature; // nhiệt độ hiện tại

    @Override
    public void attach(Observer o) {
        observers.add(o);
        System.out.println("Thiết bị đã đăng ký");
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
        System.out.println("Thiết bị đã hủy đăng ký");
    }

    @Override
    public void notifyObservers() {
        // gọi update cho tất cả observer
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println("\nCảm biến: Nhiệt độ = " + temperature);
        notifyObservers(); // mỗi lần thay đổi sẽ tự động notify
    }
}
package Session_08.Bai_04;

// Máy tạo ẩm điều chỉnh theo nhiệt độ
class Humidifier implements Observer {

    @Override
    public void update(int temperature) {
        System.out.println("Máy tạo ẩm: Điều chỉnh độ ẩm cho nhiệt độ " + temperature);
    }
}

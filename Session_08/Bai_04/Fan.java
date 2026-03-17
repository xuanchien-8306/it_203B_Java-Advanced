package Session_08.Bai_04;

// Quạt sẽ phản ứng theo nhiệt độ
class Fan implements Observer {

    @Override
    public void update(int temperature) {
        if (temperature < 20) {
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        } else if (temperature <= 25) {
            System.out.println("Quạt: Nhiệt độ trung bình, chạy mức VỪA");
        } else {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ MẠNH");
        }
    }
}

package Session_08.Bai_01;

// Singleton đảm bảo chỉ có 1 kết nối phần cứng
public class HardwareConnection {

    private static HardwareConnection instance;

    // constructor private để không cho new từ ngoài
    private HardwareConnection() {}

    // lấy instance duy nhất
    public static HardwareConnection getInstance() {

        if (instance == null) {
            instance = new HardwareConnection();    
        }

        return instance;
    }

    // kết nối phần cứng
    public void connect() {
        System.out.println("HardwareConnection: Đã kết nối phần cứng.");
    }

    public void disconnect() {
        System.out.println("HardwareConnection: Đã ngắt kết nối.");
    }
}

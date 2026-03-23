package Session_12.Bai_02;


// Chạy chương trình
public class Main {
    public static void main(String[] args) {

        PatientService service = new PatientService();

        // Test dữ liệu (37.5 vẫn OK dù máy dùng dấu phẩy)
        service.updateVitalSigns(1, 37.5, 80);
    }
}



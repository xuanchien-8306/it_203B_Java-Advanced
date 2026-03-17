package Session_08.Bai_04;

// Interface cho các thiết bị nhận thông báo
interface Observer {
    void update(int temperature); // nhận nhiệt độ mới từ sensor
}
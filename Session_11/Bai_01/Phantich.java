//package Session_11.Bai_01;
//
//Tạo connection liên tục mà không quản lý gây nguy hiểm vì:

// 1. Rò rỉ connection
// → Không close đúng cách → DB bị đầy connection → hệ thống treo

// 2. Vượt giới hạn kết nối DB
// → MySQL có max connection
// → Khi vượt → user không truy cập được

// 3. Lỗi "Communications link failure"
// → Connection cũ bị timeout nhưng vẫn giữ → gây lỗi khi dùng lại

// 4. Hiệu năng kém
// → Mỗi lần tạo connection rất tốn tài nguyên

// 5. Không đảm bảo hệ thống 24/7
// → Dễ crash, gián đoạn hệ thống bệnh viện (rất nguy hiểm)

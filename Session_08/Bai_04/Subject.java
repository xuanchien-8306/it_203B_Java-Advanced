package Session_08.Bai_04;

import java.util.*;

// Interface cho đối tượng phát thông báo
interface Subject {
    void attach(Observer o);   // đăng ký observer
    void detach(Observer o);   // hủy đăng ký
    void notifyObservers();    // gửi thông báo đến tất cả observer
}
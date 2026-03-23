package Session_12.BTTH;

import Session_12.BTTH.util.DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MainHandleDelete {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Thao tác xoá bác sĩ
        // B1: Nập id bác sĩ muốn xoá
        System.out.print("Nhập vào mã bác sĩ muốn xoá: ");
        int idDelete = sc.nextInt();
        // B2: Mở kết nối
        try(Connection conn = DB.openConnection()){
            // B3: Gọi thủ tục
            CallableStatement callableStatement = conn.prepareCall("{call proc_delete_doctor_by_id(?)}");
            callableStatement.setInt(1, idDelete);
            // B4: sử dụng dối tượng CallableStatement để gọi
            int row = callableStatement.executeUpdate();
            // B5: Thực thi câu lệnh excuteUpdate();
            System.out.println(row);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }


    }
}

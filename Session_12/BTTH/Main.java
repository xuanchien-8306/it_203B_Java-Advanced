package Session_12.BTTH;

import Session_12.BTTH.util.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try(Connection con = DB.openConnection()){
            String sql = "INSERT INTO members VALUES (?,?,?,?,?)";
            // B3: Khởi tạo đ tươợng PrepareStatement
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            // B4: set parameter (Tham số) vào
            System.out.print("Nhập mã bác sĩ: ");
            preparedStatement.setString(1, sc.nextLine());
            System.out.print("Nhập tên bác sĩ: ");
            preparedStatement.setString(2, sc.nextLine());
            System.out.print("Nhap giới tính: ");
            preparedStatement.setString(3, sc.nextLine());
            System.out.print("Nhập tuổi: ");
            preparedStatement.setInt(4, Integer.parseInt(sc.nextLine()));
            System.out.print("Nhập khoa: ");
            preparedStatement.setString(5, sc.nextLine());

            // thực thi câu lệnh
            int row = preparedStatement.executeUpdate();
            System.out.println(row);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

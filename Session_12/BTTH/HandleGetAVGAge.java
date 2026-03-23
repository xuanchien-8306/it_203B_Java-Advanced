package Session_12.BTTH;

import Session_12.BTTH.util.DB;

import java.lang.reflect.Type;
import java.sql.*;

public class HandleGetAVGAge {
    public static void main(String[] args) {
        try (Connection connection = DB.openConnection()){
            CallableStatement callableStatement = connection.prepareCall(
                    "{call proc_get_avg_age_doctor(?)}"
            );
            // register dăng ký tham số out
            callableStatement.registerOutParameter(1, Types.DOUBLE);
            // thực thi câu lệnh
            callableStatement.executeQuery();
            // lấy giá trị
            Double d = callableStatement.getDouble(1);
            System.out.println(d);
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

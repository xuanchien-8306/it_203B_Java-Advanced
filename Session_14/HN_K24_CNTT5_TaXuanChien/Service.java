package Session_14.HN_K24_CNTT5_TaXuanChien;

import java.sql.*;

public class Service {

    public static void transfer(String from, String to, double money) {
        Connection con = null;

        try {
            con = DB.getConnection();
            con.setAutoCommit(false);

            String check = "SELECT Balance FROM Accounts WHERE AccountId = ?";
            double balance = 0;

            try (PreparedStatement ps = con.prepareStatement(check)) {
                ps.setString(1, from);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    balance = rs.getDouble("Balance");
                } else {
                    throw new Exception("Lỗi");
                }
            }

            if (balance < money) {
                throw new Exception("Không đủ tiền");
            }

            String call = "{call sp_UpdateBalance(?, ?)}";
            try (CallableStatement cs = con.prepareCall(call)) {
                cs.setString(1, from);
                cs.setDouble(2, -money);
                cs.execute();

                cs.setString(1, to);
                cs.setDouble(2, money);
                cs.execute();
            }

            con.commit();

            String show = "SELECT * FROM Accounts WHERE AccountId IN (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(show)) {
                ps.setString(1, from);
                ps.setString(2, to);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println(
                            rs.getString("AccountId") + " - " +
                                    rs.getString("FullName") + " - " +
                                    rs.getDouble("Balance")
                    );
                }
            }

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
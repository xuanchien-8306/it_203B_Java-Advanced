package Session_11.BTTH.persistence;

import Session_11.BTTH.entity.Patient;
import Session_11.BTTH.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO: làm việc trực tiếp với DB
public class PatientDAO {

    // Lấy danh sách
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Patients")) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("full_name"),
                        rs.getInt("age"),
                        rs.getString("diagnosis")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        return list;
    }

    // Thêm
    public void addPatient(Patient p) {
        String sql = "INSERT INTO Patients(full_name, age, diagnosis) VALUES ('"
                + p.getFullName() + "', " + p.getAge() + ", '" + p.getDiagnosis() + "')";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int result = stmt.executeUpdate(sql);

            if (result > 0) {
                System.out.println("Đã thêm hồ sơ bệnh nhân thành công.");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    // Xóa
    public void deletePatient(int id) {
        String sql = "DELETE FROM Patients WHERE patient_id = " + id;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int result = stmt.executeUpdate(sql);

            if (result > 0) {
                System.out.println("Đã xóa 1 hồ sơ khỏi hệ thống.");
            } else {
                System.out.println("Không tìm thấy ID.");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
package Session_11.BTTH.persistence;

import Session_11.BTTH.entity.Patient;

import java.util.List;
import java.util.Scanner;

// UI + điều khiển chương trình
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PatientDAO dao = new PatientDAO();

        while (true) {
            System.out.println("\n1. Xem danh sách");
            System.out.println("2. Thêm bệnh nhân");
            System.out.println("3. Xóa bệnh nhân");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    List<Patient> list = dao.getAllPatients();
                    for (Patient p : list) {
                        System.out.println(
                                p.getId() + " | " +
                                        p.getFullName() + " | " +
                                        p.getAge() + " | " +
                                        p.getDiagnosis()
                        );
                    }
                    break;

                case 2:
                    System.out.print("Tên: ");
                    String name = sc.nextLine();

                    System.out.print("Tuổi: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Chẩn đoán: ");
                    String desc = sc.nextLine();

                    dao.addPatient(new Patient(name, age, desc));
                    break;

                case 3:
                    System.out.print("Nhập ID cần xóa: ");
                    int id = sc.nextInt();

                    dao.deletePatient(id);
                    break;

                case 4:
                    System.out.println("Thoát...");
                    return;
            }
        }
    }
}

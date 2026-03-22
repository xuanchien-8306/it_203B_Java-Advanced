package Session_11.Bai_01;

public class Main {
    public static void main(String[] args) {

        DBContext.executeQuery("select * from patient");

        DBContext.executeUpdate("insert into patient(name) values('Thang')");

        DBContext.executeQuery("select * from patient");
    }
}

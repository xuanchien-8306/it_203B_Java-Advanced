package Session_11.BTTH.entity;

// Class đại diện cho bảng Patients trong DB
public class Patient {
    private int id;
    private String fullName;
    private int age;
    private String diagnosis;

    public Patient() {}

    public Patient(int id, String fullName, int age, String diagnosis) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.diagnosis = diagnosis;
    }

    public Patient(String fullName, int age, String diagnosis) {
        this.fullName = fullName;
        this.age = age;
        this.diagnosis = diagnosis;
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public String getDiagnosis() { return diagnosis; }

    public void setId(int id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setAge(int age) { this.age = age; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
}

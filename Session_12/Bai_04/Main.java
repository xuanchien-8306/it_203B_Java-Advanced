package Session_12.Bai_04;

public class Main {
    public static void main(String[] args) {

        LabResultService service = new LabResultService();
        service.insertBatchResults();
    }

    //Statement:
    //- 1000 lần parse + 1000 execution plan
    //- Chậm, tốn tài nguyên DB
    //
    //PreparedStatement:
    //- 1 lần parse + reuse plan
    //- Nhanh hơn rõ rệt (thường nhanh hơn nhiều lần)
    //
}

package Session_10.HN_KS24_CNTT5_TaXuanChien_005;

import java.util.Scanner;

public class Document {
    private String documentId;
    private String documentName;
    private double fileSize;
    private int downloads;

    public Document() {};
    public Document(String documentId, String documentName, double fileSize, int downloads) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.fileSize = fileSize;
        this.downloads = downloads;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public void inputData(Scanner sc) {
        System.out.print("Nhập mã tài liệu: ");
        this.documentId = sc.nextLine();

        do {
            System.out.print("Nhập tên tài liệu: ");
            this.documentName = sc.nextLine();
        } while (this.documentName.trim().isEmpty());

        do {
            System.out.print("Nhập dung lượng file ( >0 ): ");
            this.fileSize = sc.nextDouble();
        }while (this.fileSize <= 0);

        do {
            System.out.println("Nhập lượt tải xuống: ");
            this.downloads = sc.nextInt();
        }while (this.downloads < 0);
    }

    public void displayData() {
        System.out.printf("| %-10s | %-20s | %-10.2f | %-10d |\n",
                documentId, documentName, fileSize, downloads);
    }

}

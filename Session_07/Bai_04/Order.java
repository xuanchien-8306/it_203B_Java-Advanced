package Session_07.Bai_04;

public class Order {

    private String orderId;
    private String customerEmail;

    public Order(String orderId, String customerEmail) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
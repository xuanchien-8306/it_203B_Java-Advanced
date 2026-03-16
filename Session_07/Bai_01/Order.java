package Session_07.Bai_01;

import java.util.ArrayList;
import java.util.List;

// Lớp chứa thông tin đơn hàng
public class Order {

    private String orderId;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private double total;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
    }

    public <Customer> Order(String ord001, Customer customer) {
    }

    // thêm sản phẩm vào đơn hàng
    public void addItem(Product product, int quantity) {

        items.add(new OrderItem(product, quantity));

        System.out.println("Đã thêm sản phẩm " + product.getId());
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
}

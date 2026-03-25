package Session_14.BTTH.entity;

public class OrderDetail {

    // id sản phẩm
    private int productId;

    // số lượng mua
    private int quantity;

    // giá tại thời điểm mua
    private double price;

    public OrderDetail(int productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}
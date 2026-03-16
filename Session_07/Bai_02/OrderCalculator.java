package Session_07.Bai_02;

// Lớp tính toán đơn hàng
public class OrderCalculator {

    private DiscountStrategy discountStrategy;

    public OrderCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculateTotal(double totalAmount) {

        // áp dụng giảm giá
        return discountStrategy.applyDiscount(totalAmount);
    }
}

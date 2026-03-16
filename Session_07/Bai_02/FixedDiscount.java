package Session_07.Bai_02;

// Giảm giá cố định một số tiền
public class FixedDiscount implements DiscountStrategy {

    private double discountAmount;

    public FixedDiscount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public double applyDiscount(double totalAmount) {

        return totalAmount - discountAmount;
    }
}
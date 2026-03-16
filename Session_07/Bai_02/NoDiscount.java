package Session_07.Bai_02;

// Không áp dụng giảm giá
public class NoDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double totalAmount) {

        return totalAmount;
    }
}
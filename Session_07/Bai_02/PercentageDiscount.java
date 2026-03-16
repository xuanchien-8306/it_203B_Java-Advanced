package Session_07.Bai_02;

// Giảm giá theo phần trăm
public class PercentageDiscount implements DiscountStrategy {

    private double percent;

    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    @Override
    public double applyDiscount(double totalAmount) {

        return totalAmount * (1 - percent / 100);
    }
}

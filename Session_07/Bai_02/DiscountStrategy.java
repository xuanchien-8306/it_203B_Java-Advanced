package Session_07.Bai_02;

// Interface định nghĩa cách áp dụng giảm giá
public interface DiscountStrategy {

    // nhận tổng tiền và trả về số tiền sau giảm
    double applyDiscount(double totalAmount);
}
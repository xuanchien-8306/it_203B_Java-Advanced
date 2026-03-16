package Session_07.Bai_01;

// Lớp tính tổng tiền đơn hàng
public class OrderCalculator {

    public double calculateTotal(Order order) {

        double total = 0;

        for (OrderItem item : order.getItems()) {
            total += item.getSubtotal();
        }

        order.setTotal(total);

        return total;
    }
}
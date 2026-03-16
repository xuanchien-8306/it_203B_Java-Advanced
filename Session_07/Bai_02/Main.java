package Session_07.Bai_02;

public class Main {

    public static void main(String[] args) {

        double total = 1000000;

        // ===== Test 1: PercentageDiscount 10% =====
        System.out.println("Input: Đơn hàng tổng tiền 1.000.000, giảm giá 10%");
        OrderCalculator calc1 =
                new OrderCalculator(new PercentageDiscount(10));

        double result1 = calc1.calculateTotal(total);
        System.out.println("Output: Số tiền sau giảm: " + result1);
        System.out.println();


        // ===== Test 2: FixedDiscount =====
        System.out.println("Input: Đơn hàng tổng tiền 1.000.000, giảm cố định 50.000");
        OrderCalculator calc2 =
                new OrderCalculator(new FixedDiscount(50000));

        double result2 = calc2.calculateTotal(total);
        System.out.println("Output: Số tiền sau giảm: " + result2);
        System.out.println();


        // ===== Test 3: NoDiscount =====
        System.out.println("Input: Đơn hàng tổng tiền 1.000.000, không giảm giá");
        OrderCalculator calc3 =
                new OrderCalculator(new NoDiscount());

        double result3 = calc3.calculateTotal(total);
        System.out.println("Output: Số tiền sau giảm: " + result3);
        System.out.println();


        // ===== Test 4: HolidayDiscount =====
        System.out.println("Input: Đơn hàng tổng tiền 1.000.000, giảm lễ 15%");
        OrderCalculator calc4 =
                new OrderCalculator(new HolidayDiscount());

        double result4 = calc4.calculateTotal(total);
        System.out.println("Output: Số tiền sau giảm: " + result4);
    }
}
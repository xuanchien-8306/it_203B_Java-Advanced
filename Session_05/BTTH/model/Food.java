package Session_05.BTTH.model;

public class Food extends MenuItem {
    private int calories;

    public Food(String id, String name, double basePrice, int calories) {
        super(id, name, basePrice);
        this.calories = calories;
    }

    @Override
    public double calculatePrice() {
        // Đối với món ăn, giá bán bằng giá cơ bản
        return getBasePrice();
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Calo: %d kcal", calories);
    }
}
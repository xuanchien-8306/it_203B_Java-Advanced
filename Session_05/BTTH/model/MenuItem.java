package Session_05.BTTH.model;

public class MenuItem {
    private String id;
    private String name;
    private double basePrice;

    public MenuItem(String id, String name, double basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    public double calculatePrice() {
        return 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }
    @Override
    public String toString() {
        return String.format("[%s] %-15s | Giá: %.2f", id, name, calculatePrice());
    }
}

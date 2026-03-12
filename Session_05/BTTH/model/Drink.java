package Session_05.BTTH.model;

public class Drink extends MenuItem {
    private String size;

    public Drink(String id, String name, double basePrice, String size) {
        super(id, name, basePrice);
        this.size = size.toUpperCase();
    }

    @Override
    public double calculatePrice() {
        switch (size) {
            case "M":
                return getBasePrice() + 5.0;
            case "L":
                return getBasePrice() + 10.0;
            default:
                return getBasePrice();
        }
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Size: %s", size);
    }
}
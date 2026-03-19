package entity;

public class StandardVehicle extends Vehicle {

    public StandardVehicle(String id, Intersection i, TrafficLight l) {
        super(id, i, l);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
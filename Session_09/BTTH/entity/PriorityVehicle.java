package entity;

public class PriorityVehicle extends Vehicle {

    public PriorityVehicle(String id, Intersection i, TrafficLight l) {
        super(id, i, l);
    }

    @Override
    public boolean isPriority() {
        return true;
    }
}
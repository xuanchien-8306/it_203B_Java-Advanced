package pattern.factory;

import entity.*;

import java.util.Random;

public class VehicleFactory {

    private static Random rand = new Random();

    public static Vehicle createVehicle(int id, Intersection i, TrafficLight l) {

        int r = rand.nextInt(4);

        if (r == 0) {
            return new PriorityVehicle("Ambulance-" + id, i, l);
        } else if (r == 1) {
            return new StandardVehicle("Car-" + id, i, l);
        } else if (r == 2) {
            return new StandardVehicle("Bike-" + id, i, l);
        } else {
            return new StandardVehicle("Truck-" + id, i, l);
        }
    }
}
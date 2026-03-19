package entity;

import pattern.observer.Observer;
import util.TimeUtil;

public abstract class Vehicle implements Runnable, Observer {

    protected String id;
    protected Intersection intersection;
    protected TrafficLight light;

    public Vehicle(String id, Intersection intersection, TrafficLight light) {
        this.id = id;
        this.intersection = intersection;
        this.light = light;
        light.addObserver(this);
    }

    public String getId() {
        return id;
    }

    public TrafficLight getLight() {
        return light;
    }

    public abstract boolean isPriority();

    @Override
    public void run() {
        intersection.enter(this);

        // 🔥 QUAN TRỌNG
        light.removeObserver(this);
    }

    @Override
    public void update(String state) {
        System.out.println("[" + TimeUtil.now() + "s] " + id + " thấy đèn: " + state);
    }
}
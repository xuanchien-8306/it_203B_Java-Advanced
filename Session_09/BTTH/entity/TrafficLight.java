package entity;

import pattern.observer.*;
import util.TimeUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TrafficLight implements Runnable, Subject {

    private String state = "GREEN";
    private boolean running = true;

    private Intersection intersection;

    public TrafficLight(Intersection intersection) {
        this.intersection = intersection;
    }

    public boolean isGreen() {
        return "GREEN".equals(state);
    }

    @Override
    public void run() {

        while (running) {
            try {

                switch (state) {
                    case "GREEN":
                        state = "YELLOW";
                        synchronized (System.out) {
                            System.out.println("[" + TimeUtil.now() + "s] 🚦 YELLOW");
                        }
                        Thread.sleep(2000);
                        break;

                    case "YELLOW":
                        state = "RED";
                        synchronized (System.out) {
                            System.out.println("[" + TimeUtil.now() + "s] 🚦 RED");
                        }
                        Thread.sleep(4000);
                        break;

                    case "RED":
                        state = "GREEN";
                        synchronized (System.out) {
                            System.out.println("[" + TimeUtil.now() + "s] 🚦 GREEN");
                        }
                        Thread.sleep(4000);
                        break;
                }

                notifyObservers();
                intersection.onLightChange();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }

    // ===== Observer =====

    private List<Observer> observers = new CopyOnWriteArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(state);
        }
    }
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
}
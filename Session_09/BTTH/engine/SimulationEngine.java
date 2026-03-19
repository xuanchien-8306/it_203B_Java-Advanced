package engine;

import entity.*;
import pattern.factory.VehicleFactory;
import util.TimeUtil;
import java.util.concurrent.*;

public class SimulationEngine {

    public void start() {
        TimeUtil.start();
        Intersection intersection = new Intersection();
        TrafficLight light = new TrafficLight(intersection);

        Thread lightThread = new Thread(light);
        lightThread.setDaemon(true);
        lightThread.start();

        ExecutorService pool = Executors.newFixedThreadPool(6);

        long start = System.currentTimeMillis();
        long duration = 12000;

        int id = 0;

        while (System.currentTimeMillis() - start < duration) {

            Vehicle v = VehicleFactory.createVehicle(id++, intersection, light);
            pool.execute(v);

            try {
                Thread.sleep(700);
            } catch (InterruptedException ignored) {}
        }

        light.stop();
        pool.shutdown();

        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n===== KẾT THÚC =====");
        System.out.println("Xe đã qua: " + intersection.getPassedCount());
        System.out.println("Số lần kẹt xe: " + intersection.getJamCount());
    }
}
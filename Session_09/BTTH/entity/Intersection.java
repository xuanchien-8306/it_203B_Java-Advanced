package entity;

import exception.TrafficJamException;
import util.TimeUtil;

public class Intersection {
    private int jamCount = 0;
    private int carsInIntersection = 0;
    private final int MAX = 3;

    private int passed = 0;

    public synchronized void enter(Vehicle v) {

        long waitStart = System.currentTimeMillis();

        try {
            while (!canPass(v)) {

                long waited = System.currentTimeMillis() - waitStart;

                // 🔥 nếu chờ quá 5s -> kẹt xe
                if (waited > 5000) {
                    throw new TrafficJamException(v.getId() + " bị kẹt xe quá lâu!");
                }

                wait(500); // đợi 0.5s rồi check lại
            }

            carsInIntersection++;

            System.out.println("[" + TimeUtil.now() + "s] " + v.getId() + " đi qua");

            Thread.sleep(500);

            carsInIntersection--;
            passed++;

            notifyAll();

        } catch (TrafficJamException e) {

                jamCount++; // 🔥 tăng ở đây

            System.out.println("[" + TimeUtil.now() + "s] 🚨 KẸT XE: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public int getJamCount() {
        return jamCount;
    }

    private boolean canPass(Vehicle v) {

        if (v.isPriority()) return true;

        return v.getLight().isGreen() && carsInIntersection < MAX;
    }

    public synchronized void onLightChange() {
        notifyAll();
    }

    public int getPassedCount() {
        return passed;
    }
}
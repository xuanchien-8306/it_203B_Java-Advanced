package util;

public class TimeUtil {

    public static long startTime;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static double now() {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }
}
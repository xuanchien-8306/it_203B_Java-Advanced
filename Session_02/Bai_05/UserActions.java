package Session_02.Bai_05;

public interface UserActions {

    default void logActivity(String activity) {
        System.out.println("User activity: " + activity);
    }
}
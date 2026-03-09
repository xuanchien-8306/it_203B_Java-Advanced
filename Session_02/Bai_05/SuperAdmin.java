package Session_02.Bai_05;

public class SuperAdmin implements UserActions, AdminActions {

    @Override
    public void logActivity(String activity) {
        UserActions.super.logActivity(activity);
        AdminActions.super.logActivity(activity);
    }
}
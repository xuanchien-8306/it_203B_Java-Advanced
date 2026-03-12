package Session_04.Bai_05;

public class AccessService {

    public boolean canPerformAction(User user, Action action) {

        switch (user.getRole()) {

            case ADMIN:
                return true;

            case MODERATOR:
                return action == Action.LOCK_USER ||
                        action == Action.VIEW_PROFILE;

            case USER:
                return action == Action.VIEW_PROFILE;

            default:
                return false;
        }
    }
}

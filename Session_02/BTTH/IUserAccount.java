package Session_02.BTTH;

public interface IUserAccount {

    String getRole();

    default boolean checkAccess() {
        return "ADMIN".equals(getRole());
    }

    static boolean isStandardLength(String username) {
        return username != null && username.length() > 5;
    }
}
package Session_02.Bai_03;

public class Main {
    public static void main(String[] args) {

        User user = new User("kien", "123456");

        System.out.println("Authenticated: " + user.isAuthenticated());

        String encrypted = Authenticatable.encrypt(user.getPassword());
        System.out.println("Encrypted password: " + encrypted);
    }
}
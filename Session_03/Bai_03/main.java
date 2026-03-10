package Session_03.Bai_03;

import java.util.Optional;

public class main {
    public static void main(String[] args) {
        UserRepository repo = new UserRepository();
        Optional<User> user = repo.findUsername("alice");
        user.ifPresent(u -> System.out.println("Welcome " + u.username()));

        System.out.println(user.map(u -> "Welcome " + u.username()).orElse("Guest login"));
    }
}

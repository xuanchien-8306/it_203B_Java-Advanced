package Session_02.Bai_01;

import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {

        User user = new User("kien123", "ADMIN");

        Predicate<User> isAdmin = u -> u.getRole().equals("ADMIN");
        System.out.println("Is Admin: " + isAdmin.test(user));

        Function<User, String> getUsername = u -> u.getUsername();
        System.out.println("Username: " + getUsername.apply(user));

        Consumer<User> printUser = u -> System.out.println("User Info: " + u);
        printUser.accept(user);

        Supplier<User> createUser = () -> new User("guest", "USER");
        User newUser = createUser.get();
        System.out.println("New User: " + newUser);
    }
}


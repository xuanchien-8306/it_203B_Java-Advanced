package Session_02.Bai_04;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        users.add(new User("kien"));
        users.add(new User("anh"));
        users.add(new User("minh"));

        Function<User, String> getUsername = User::getUsername;
        Consumer<String> print = System.out::println;
        Supplier<User> createUser = User::new;

        users.stream()
                .map(getUsername)
                .forEach(print);

        User newUser = createUser.get();
        print.accept(newUser.getUsername());
    }
}

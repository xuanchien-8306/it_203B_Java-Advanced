package Session_03.Bai_05;

import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alexander", "a@gmail.com"),
                new User("bob", "b@gmail.com"),
                new User("charlotte", "c@gmail.com"),
                new User("Benjamin", "d@gmail.com"),
                new User("tom", "e@gmail.com"),
                new User("michael", "f@gmail.com")
        );

        users.stream()
                .sorted(Comparator.comparingInt((User u) -> u.username().length()).reversed())                .limit(3)
                .map(User::username)
                .forEach(System.out::println);
    }
}

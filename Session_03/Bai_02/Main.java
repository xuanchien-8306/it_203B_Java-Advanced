package Session_03.Bai_02;

import java.util.List;

public class Main{
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alice", "alice@gmail.com"),
                new User("bob", "bob@yahoo.com"),
                new User("charlie", "charlie@gmail.com")
        );

        users.stream()
                .filter(user -> user.email().endsWith("@gmail.com"))
                .map(User::userName)
                .forEach(System.out::println);
    }
}

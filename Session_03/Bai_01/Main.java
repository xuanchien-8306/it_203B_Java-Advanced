package Session_03.Bai_01;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alice", "alice@gmail.com", Status.ACTIVE),
                new User("bob", "bob@gmail.com", Status.INACTIVE),
                new User("charlie", "charlie@gmail.com", Status.ACTIVE)
        );

        users.forEach(user -> System.out.println(user));
    }
}

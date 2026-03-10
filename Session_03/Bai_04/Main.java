package Session_03.Bai_04;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice1@gmail.com"),
                new User("bob", "bob@gmail.com"),
                new User("alice", "alice2@gmail.com"),
                new User("charlie", "charlie@gmail.com"),
                new User("bob", "bob2@gmail.com")
        );

        List<User> uniqueUsers = new ArrayList<>(
                users.stream()
                        .collect(Collectors.toMap(
                                User::username,
                                user -> user,
                                (existing, replacement) -> existing
                        ))
                        .values()
        );

        uniqueUsers.forEach(System.out::println);
    }
}
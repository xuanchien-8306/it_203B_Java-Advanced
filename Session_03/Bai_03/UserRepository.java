package Session_03.Bai_03;

import java.util.List;
import java.util.Optional;

public class UserRepository {
    private List<User> users = List.of(
            new User("alice", "alice@gmail.com"),
            new User("bob", "bob@yahoo.com"),
            new User("charlie", "charlie@gmail.com")
    );

    public Optional<User> findUsername(String username) {
        return users.stream()
                .filter(user -> user.email().equals(username))
                .findFirst();
    }


}

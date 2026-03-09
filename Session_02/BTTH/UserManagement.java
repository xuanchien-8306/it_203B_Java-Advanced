package Session_02.BTTH;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class UserManagement {

    Supplier<User> createUser = User::new;

    Predicate<User> isActive = u -> "ACTIVE".equals(u.getStatus());

    Function<User, String> extractEmail = User::getEmail;
}
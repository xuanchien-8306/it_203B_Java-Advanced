package Session_02.BTTH;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserManagement manager = new UserManagement();

        User user = manager.createUser.get();

        boolean valid = IUserAccount.isStandardLength("kien123");
        System.out.println(valid);

        User u1 = new User("kien123", "kien@gmail.com", "ADMIN", "ACTIVE");
        String email = manager.extractEmail.apply(u1);
        System.out.println(email);

        List<User> users = new ArrayList<>();
        users.add(new User("kien123", "kien@gmail.com", "ADMIN", "ACTIVE"));
        users.add(new User("anh234", "anh@gmail.com", "USER", "ACTIVE"));
        users.add(new User("minh345", "minh@gmail.com", "USER", "INACTIVE"));
        users.add(new User("long456", "long@gmail.com", "USER", "ACTIVE"));

        users.forEach(System.out::println);
    }
}
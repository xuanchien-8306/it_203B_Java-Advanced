package Session_03.BTTH;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        UserService service = new UserService();

        List<User> users = List.of(
                new User("U01","a@gmail.com","123",true, LocalDate.of(2020,1,1)),
                new User("U02","b@gmail.com","123",false, LocalDate.of(2023,3,10)),
                new User("U03","c@gmail.com","123",true, LocalDate.of(2022,5,5)),
                new User("U04","d@gmail.com","123",true, LocalDate.of(2024,1,1)),
                new User("U05","e@gmail.com","123",false, LocalDate.of(2021,8,15))
        );

        List<User> verifiedUsers = service.getVerifiedUsers(users);

        List<PublicUser> publicUsers = verifiedUsers.stream().
                map(user -> {
                    long months = Period.between(user.createAt(), LocalDate.now()).toTotalMonths();
                    Tier tier = service.classifyTier(months);
                    return new PublicUser(
                            user.id(),
                            user.email(),
                            tier
                    );
                }).collect(Collectors.toList());
        System.out.println("""
                ===== USER REPORT =====
                """);
        publicUsers.forEach(System.out::println);
    }
}

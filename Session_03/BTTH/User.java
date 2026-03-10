package Session_03.BTTH;
import java.time.LocalDate;

public record User(
        String id,
        String email,
        String password,
        boolean verified,
        LocalDate createAt

) {}

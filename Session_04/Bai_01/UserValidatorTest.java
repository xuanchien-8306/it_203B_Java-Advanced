package Session_04.Bai_01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    UserValidator validator = new UserValidator();

    @Test
    void TC01_validUsername() {

        // Arrange
        String username = "user123";

        // Act
        boolean result = validator.isValidUsername(username);

        // Assert
        assertTrue(result);
    }

    @Test
    void TC02_usernameTooShort() {

        // Arrange
        String username = "abc";

        // Act
        boolean result = validator.isValidUsername(username);

        // Assert
        assertFalse(result);
    }

    @Test
    void TC03_usernameContainsSpace() {

        // Arrange
        String username = "user name";

        // Act
        boolean result = validator.isValidUsername(username);

        // Assert
        assertFalse(result);
    }
}

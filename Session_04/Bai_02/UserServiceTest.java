package Session_04.Bai_02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    UserService service = new UserService();

    @Test
    void testAge18_validBoundary() {

        // Arrange
        int age = 18;

        // Act
        boolean result = service.checkRegistrationAge(age);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void testAge17_invalid() {

        // Arrange
        int age = 17;

        // Act
        boolean result = service.checkRegistrationAge(age);

        // Assert
        assertEquals(false, result);
    }

    @Test
    void testNegativeAge_throwException() {

        // Arrange
        int age = -1;

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> service.checkRegistrationAge(age)
        );
    }
}

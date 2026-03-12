package Session_04.Bai_03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserProcessorTest {

    private UserProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new UserProcessor();
    }

    @Test
    void shouldReturnSameEmail_whenEmailIsValid() {

        // Arrange
        String email = "user@gmail.com";

        // Act
        String result = processor.processEmail(email);

        // Assert
        assertEquals("user@gmail.com", result);
    }

    @Test
    void shouldThrowException_whenEmailMissingAtSymbol() {

        // Arrange
        String email = "usergmail.com";

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> processor.processEmail(email)
        );
    }

    @Test
    void shouldThrowException_whenEmailHasNoDomain() {

        // Arrange
        String email = "user@";

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> processor.processEmail(email)
        );
    }

    @Test
    void shouldConvertEmailToLowercase() {

        // Arrange
        String email = "Example@Gmail.com";

        // Act
        String result = processor.processEmail(email);

        // Assert
        assertEquals("example@gmail.com", result);
    }
}

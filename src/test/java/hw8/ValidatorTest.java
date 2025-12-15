package hw8;

import org.junit.jupiter.api.Test;
import hw8.model.User;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testValidUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(25);
        user.setPassword("secure123");

        ValidationResult result = Validator.validate(user);

        assertTrue(result.isValid());
        assertEquals(0, result.getErrors().size());
    }

    @Test
    void testNullName() {
        User user = new User();
        user.setName(null);
        user.setEmail("test@example.com");
        user.setAge(30);
        user.setPassword("password");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertEquals(2, result.getErrors().size()); // @NotNull и @Size (для null)
        assertTrue(result.getErrors().contains("Имя не может быть null"));
    }

    @Test
    void testInvalidEmail() {
        User user = new User();
        user.setName("Valid Name");
        user.setEmail("invalid-email");
        user.setAge(30);
        user.setPassword("password");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Некорректный формат email"));
    }

    @Test
    void testAgeOutOfRange() {
        User user = new User();
        user.setName("Valid Name");
        user.setEmail("valid@example.com");
        user.setAge(200);
        user.setPassword("password");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Возраст должен быть от 0 до 150"));
    }

    @Test
    void testShortPassword() {
        User user = new User();
        user.setName("Valid Name");
        user.setEmail("valid@example.com");
        user.setAge(25);
        user.setPassword("123");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains("Пароль должен быть от 6 до 20 символов"));
    }

    @Test
    void testMultipleErrors() {
        User user = new User();
        user.setName("A"); // слишком короткое имя
        user.setEmail(null); // null email
        user.setAge(-5); // отрицательный возраст
        user.setPassword("123"); // короткий пароль

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().size() >= 4);
    }

    @Test
    void testBoundaryValues() {
        User user = new User();
        user.setName("AB"); // ровно 2 символа
        user.setEmail("a@b.c"); // минимальный валидный email
        user.setAge(0); // минимальный возраст
        user.setPassword("123456"); // ровно 6 символов

        ValidationResult result = Validator.validate(user);

        assertTrue(result.isValid());
    }

    @Test
    void testNullObject() {
        ValidationResult result = Validator.validate(null);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertTrue(result.getErrors().contains("Object to validate cannot be null"));
    }

    @Test
    void testAllAnnotations() {
        // Создаем пользователя с ошибками для каждой аннотации
        User user = new User(null, "invalid", 200, "123");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("не может быть null")));
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Некорректный формат email")));
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Возраст должен быть от")));
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Пароль должен быть от")));
    }
}
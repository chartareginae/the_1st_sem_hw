package hw8;

import hw8.model.User;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setName("A");
        user.setEmail("email");
        user.setAge(25);
        user.setPassword("123");

        ValidationResult result = Validator.validate(user);

        if (!result.isValid()) {
            System.out.println("Ошибки валидации:");
            result.getErrors().forEach(System.out::println);
        } else {
            System.out.println("Валидация прошла успешно!");
        }
    }
}
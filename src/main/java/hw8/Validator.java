package hw8;

import hw8.annotations.Email;
import hw8.annotations.NotNull;
import hw8.annotations.Range;
import hw8.annotations.Size;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class Validator {

    public static ValidationResult validate(Object object) {
        ValidationResult result = new ValidationResult();

        if (object == null) {
            result.addError("Object to validate cannot be null");
            return result;
        }

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(object);

                // Проверка @NotNull
                if (field.isAnnotationPresent(NotNull.class)) {
                    NotNull annotation = field.getAnnotation(NotNull.class);
                    if (value == null) {
                        result.addError(annotation.message());
                    }
                }

                // Если поле null, пропускаем остальные проверки (кроме @NotNull)
                if (value == null) {
                    continue;
                }

                // Проверка @Size для строк
                if (field.isAnnotationPresent(Size.class) && value instanceof String) {
                    Size annotation = field.getAnnotation(Size.class);
                    String strValue = (String) value;
                    int length = strValue.length();

                    if (length < annotation.min() || length > annotation.max()) {
                        String message = annotation.message()
                                .replace("{min}", String.valueOf(annotation.min()))
                                .replace("{max}", String.valueOf(annotation.max()));
                        result.addError(message);
                    }
                }

                // Проверка @Range для чисел
                if (field.isAnnotationPresent(Range.class)) {
                    Range annotation = field.getAnnotation(Range.class);
                    long numericValue = 0;

                    if (value instanceof Integer) {
                        numericValue = ((Integer) value).longValue();
                    } else if (value instanceof Long) {
                        numericValue = (Long) value;
                    } else if (value instanceof Short) {
                        numericValue = ((Short) value).longValue();
                    } else if (value instanceof Byte) {
                        numericValue = ((Byte) value).longValue();
                    } else if (value instanceof Double) {
                        numericValue = ((Double) value).longValue();
                    } else if (value instanceof Float) {
                        numericValue = ((Float) value).longValue();
                    }

                    if (numericValue < annotation.min() || numericValue > annotation.max()) {
                        String message = annotation.message()
                                .replace("{min}", String.valueOf(annotation.min()))
                                .replace("{max}", String.valueOf(annotation.max()));
                        result.addError(message);
                    }
                }

                // Проверка @Email для строк
                if (field.isAnnotationPresent(Email.class) && value instanceof String) {
                    Email annotation = field.getAnnotation(Email.class);
                    String email = (String) value;

                    Pattern pattern = Pattern.compile(annotation.pattern());
                    if (!pattern.matcher(email).matches()) {
                        result.addError(annotation.message());
                    }
                }

            } catch (IllegalAccessException e) {
                result.addError("Cannot access field: " + field.getName());
            } finally {
                field.setAccessible(false);
            }
        }

        return result;
    }
}
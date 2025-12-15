package hw8;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private boolean valid;
    private List<String> errors;

    public ValidationResult() {
        this.valid = true;
        this.errors = new ArrayList<>();
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public void addError(String error) {
        this.errors.add(error);
        this.valid = false;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "valid=" + valid +
                ", errors=" + errors +
                '}';
    }
}
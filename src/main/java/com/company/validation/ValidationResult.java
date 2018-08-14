package com.company.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private boolean valid;
    private final List<ValidationError> errors = new ArrayList<>();

    ValidationResult() {
        this.valid = true;
    }

    void addError() {
        this.valid = false;
        this.errors.add(ValidationError.GENERIC);
    }

    void addError(ValidationError error, String found) {
        this.valid = false;
        error.setFound(found);
        this.errors.add(error);
    }

    public boolean isValid() {
        return valid;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}

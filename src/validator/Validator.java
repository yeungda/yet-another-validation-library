package validator;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    private Input input;
    private ValidationMatcher validationMatcher;

    public void validateThat(Input input, ValidationMatcher validationMatcher) {
        this.input = input;
        this.validationMatcher = validationMatcher;
    }

    public void describeErrors(List<ValidationError> validationErrors) {
        validationErrors.add(new ValidationError());
    }
}

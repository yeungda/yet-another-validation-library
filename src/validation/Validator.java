package validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Validator {
    private final Collection<ValidationError> validationErrors = new ArrayList<ValidationError>(10);
    private final Collection<ErrorId> alreadyValidated = new ArrayList<ErrorId>(10);

    public void validateThat(Field field, ValidationMatcher validationMatcher) {
        final ErrorId errorId = new ErrorId();
        field.describeTo(errorId);
        if (hasNoRecordedErrorFor(errorId) && field.matches(validationMatcher)) {
            alreadyValidated.add(errorId);
            validationErrors.add(createValidationError(field, validationMatcher));
        }
    }

    private ValidationError createValidationError(Field field, ValidationMatcher validationMatcher) {
        final ValidationError validationError = new ValidationError();
        field.describeTo(validationError);
        validationMatcher.describeTo(validationError);
        return validationError;
    }

    private boolean hasNoRecordedErrorFor(ErrorId errorId) {
        return !alreadyValidated.contains(errorId);
    }

    public void describeErrors(List<ValidationError> validationErrors) {
        for (ValidationError error : this.validationErrors) {
            validationErrors.add(error);
        }
    }

}

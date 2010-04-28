package validation.core;

import domain.Controller;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Validator {
    private final Collection<ValidationError> validationErrors = new ArrayList<ValidationError>(10);
    private final Collection<ErrorId> alreadyValidated = new ArrayList<ErrorId>(10);

    public void validateThat(Field field, Matcher<? extends String> matcher) {
        final ErrorId errorId = new ErrorId();
        field.describeTo(errorId);
        if (hasNoRecordedErrorFor(errorId) && !field.matches(matcher)) {
            alreadyValidated.add(errorId);
            validationErrors.add(createValidationError(field, matcher));
        }
    }

    private ValidationError createValidationError(Field field, Matcher<? extends String> matcher) {
        final ValidationError validationError = new ValidationError();
        field.describeTo(validationError);
        final StringDescription stringDescription = new StringDescription();
        matcher.describeTo(stringDescription);
        validationError.setDescription(stringDescription.toString());
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

    public void describeErrorsTo(ErrorMessageWriter errorMessageWriter) {
        for (ValidationError validationError : this.validationErrors) {
            validationError.describeTo(errorMessageWriter);
        }
    }
}

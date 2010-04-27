package domain;

import validation.ValidationError;
import validation.ValidationMatcher;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationMatchers {

    public static ValidationMatcher isMandatory() {
        return new ValidationMatcherWithMessage("failed.is.mandatory") {
            @Override
            public boolean passes(String value) {
                return "".equals(value);
            }
        };
    }

    //TODO: consider composing this such that: hasCharacterLength(lessThan(30))
    // change would require composition of error messages.
    public static ValidationMatcher hasLessCharactersThan(final int numberOfCharacters) {
        return new ValidationMatcherWithMessage("failed.has.less.characters.than") {
            @Override
            public boolean passes(String value) {
                return value.length() < numberOfCharacters;
            }
        };
    }

    public static ValidationMatcher isADate() {
        return new ValidationMatcherWithMessage("failed.is.a.date") {
            @Override
            public boolean passes(String value) {
                if (!value.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}")) {
                    return false;
                }
                try {
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dateFormat.setLenient(false);
                    dateFormat.parse(value);
                    return true;
                } catch (ParseException e) {
                    return false;
                }
            }
        };
    }

    public abstract static class ValidationMatcherWithMessage implements ValidationMatcher {
        private final String message;

        public ValidationMatcherWithMessage(String message) {
            this.message = message;
        }

        @Override
        public void describeTo(ValidationError validationError) {
            validationError.setMessage(message);
        }
    }

    public static ValidationMatcher isAWholeNumber() {
        return new ValidationMatcherWithMessage("failed.is.a.whole.number") {
            @Override
            public boolean passes(String value) {
                return value.matches("^[0-9]+$");
            }
        };
    }
}

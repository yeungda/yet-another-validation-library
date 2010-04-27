package validation;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class ValidatorUnitTest {
    private static final Field FIELD = new Field("field", "");

    @Test
    public void shouldValidateNoFields() {
        final Validator validator = new Validator();
        assertThat(reportOf(validator), hasSize(0));
    }
    
    @Test
    public void shouldValidateASingleField() {
        assertThat(validating(FIELD, isNeverGoodEnough()), hasErrorMessageForField("field", "never good enough"));
        assertThat(validating(FIELD, isNeverGoodEnough(), isNeverGoodEnoughForOtherReasons()),
                allOf(
                        hasErrorMessageForField("field", "never good enough"),
                        not(hasErrorMessageForField("field", "other reasons"))
                )
        );
        assertThat(validating(FIELD, isAlwaysPerfect(), isNeverGoodEnough()), hasErrorMessageForField("field", "never good enough"));
        assertThat(validating(FIELD, isAlwaysPerfect()), hasSize(0));
    }

    @Test
    public void shouldValidateMultipleFields() {
        final Validator validator = new Validator();
        validator.validateThat(new Field("a", ""), isNeverGoodEnough());
        validator.validateThat(new Field("b", ""), isNeverGoodEnough());
        assertThat(reportOf(validator),
                allOf(
                        hasErrorMessageForField("a", "never good enough"),
                        hasErrorMessageForField("b", "never good enough")
                )
        );
    }

    private ValidationMatcher isNeverGoodEnoughForOtherReasons() {
        return isNeverGoodEnoughBecause("other reasons");
    }

    private ValidationMatcher isNeverGoodEnoughBecause(final String message) {
        return new ValidationMatcher() {
            @Override
            public void describeTo(ValidationError validationError) {
                validationError.setMessage(message);
            }

            @Override
            public boolean passes(String value) {
                return true;
            }
        };
    }

    private ValidationMatcher isAlwaysPerfect() {
        return new ValidationMatcher() {
            @Override
            public void describeTo(ValidationError validationError) {
                validationError.setMessage("should never happen");
            }

            @Override
            public boolean passes(String value) {
                return false;
            }
        };
    }

    private ValidationMatcher isNeverGoodEnough() {
        return isNeverGoodEnoughBecause("never good enough");
    }

    private Collection<ValidationError> validating(Field field, ValidationMatcher... matchers) {
        Validator validator = new Validator();
        for (ValidationMatcher matcher : matchers) {
            validator.validateThat(field, matcher);
        }
        return reportOf(validator);
    }

    private Collection<ValidationError> reportOf(Validator validator) {
        final List<ValidationError> validationErrors = new ArrayList<ValidationError>();
        validator.describeErrors(validationErrors);
        return validationErrors;
    }

    private Matcher<Iterable<ValidationError>> hasErrorMessageForField(String fieldName, String message) {
        return hasItem(fieldWithError(fieldName, message));
    }

    private Matcher<ValidationError> fieldWithError(final String fieldName, final String message) {
        return new TypeSafeMatcher<ValidationError>() {

            @Override
            public boolean matchesSafely(ValidationError validationError) {
                final HashMap<String, String> map = new HashMap<String, String>();
                validationError.describeTo(map);
                return fieldName.equals(map.get("fieldName")) && message.equals(map.get("message"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Field Name ");
                description.appendValue(fieldName);
                description.appendText(" with Message");
                description.appendValue(message);
            }
        };
    }
}

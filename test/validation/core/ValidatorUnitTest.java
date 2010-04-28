package validation.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import validation.core.Field;
import validation.core.ValidationError;
import validation.core.Validator;

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
        assertThat(reportOf(new Validator()), hasSize(0));
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

    private Matcher<? extends String> isNeverGoodEnoughForOtherReasons() {
        return isNeverGoodEnoughBecause("other reasons");
    }

    private Matcher<? extends String> isAlwaysPerfect() {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should never happen");
            }
        };
    }

    private Matcher<? extends String> isNeverGoodEnough() {
        return isNeverGoodEnoughBecause("never good enough");
    }

    private TypeSafeMatcher<String> isNeverGoodEnoughBecause(final String reason) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(reason);
            }
        };
    }

    private Collection<ValidationError> validating(Field field, Matcher<? extends String>... matchers) {
        Validator validator = new Validator();
        for (Matcher<? extends String> matcher : matchers) {
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

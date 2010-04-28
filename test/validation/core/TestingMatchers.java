package validation.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.HashMap;

import static org.junit.matchers.JUnitMatchers.hasItem;

public class TestingMatchers {
    public static Matcher<ValidationError> fieldWithError(final String fieldName, final String message) {
        return new TypeSafeMatcher<ValidationError>() {

            @Override
            public boolean matchesSafely(ValidationError validationError) {
                final HashMap<String, String> map = new HashMap<String, String>();
                validationError.describeTo(map);
                return fieldName.equals(map.get("fieldName")) && message.equals(map.get("description"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Field Name ");
                description.appendValue(fieldName);
                description.appendText(" with description ");
                description.appendValue(message);
            }
        };
    }

    public static Matcher<Iterable<ValidationError>> hasErrorMessageForField(String fieldName, String message) {
        return hasItem(fieldWithError(fieldName, message));
    }
}

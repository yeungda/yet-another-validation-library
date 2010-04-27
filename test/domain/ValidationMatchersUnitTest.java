package domain;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;
import validation.ValidationError;
import validation.ValidationMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static domain.ValidationMatchers.*;
import static domain.ValidationMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ValidationMatchersUnitTest {
    @Test
    public void mandatory() {
        assertThat("", passes(isMandatory()));
        assertThat("x", fails(isMandatory()));
        assertThat(reportFor(isMandatory()), hasMessage("failed.is.mandatory"));
    }

    @Test
    public void lessCharactersThanX() {
        assertThat("", passes(hasLessCharactersThan(1)));
        assertThat("1", fails(hasLessCharactersThan(1)));
        assertThat(reportFor(hasLessCharactersThan(1)), hasMessage("failed.has.less.characters.than"));
    }

    @Test
    public void wholeNumber() {
        assertThat(forEveryCharacterIn("0123456789"), everyItem(passes(isAWholeNumber())));
        assertThat(forEveryCharacterIn("abc"), everyItem(fails(isAWholeNumber())));
        assertThat(reportFor(isAWholeNumber()), hasMessage("failed.is.a.whole.number"));
    }

    @Test
    @Ignore("TODO")
    public void numberBetween() {

    }

    @Test
    public void date() {
        assertThat("10/10/2000", passes(isADate()));
        assertThat("1/1/2000", passes(isADate()));
        assertThat("1/1/10", fails(isADate()));
        assertThat("12/13/2000", fails(isADate()));
        assertThat("x", fails(isADate()));
        assertThat(reportFor(isADate()), hasMessage("failed.is.a.date"));
    }

    @Test
    @Ignore("TODO")
    public void dateBetween() {

    }

    @Test
    public void email() {

    }

    private Matcher<String> fails(ValidationMatcher validationMatcher) {
        return not(passes(validationMatcher));
    }

    private Iterable<String> forEveryCharacterIn(String s) {
        final ArrayList<String> strings = new ArrayList<String>();
        for (char character : s.toCharArray()) {
            strings.add(String.valueOf(character));
        }
        return strings;
    }

    private Matcher<Map<? extends String, ? extends String>> hasMessage(String message) {
        return hasEntry("message", message);
    }

    private Matcher<String> passes(final ValidationMatcher validationMatcher) {
        return new org.junit.internal.matchers.TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String value) {
                return validationMatcher.passes(value);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("a value that passes validation for the ValidationMatcher ");
                description.appendValue(reportFor(validationMatcher).get("message"));
            }
        };
    }

    private Matcher<? super Boolean> isValid() {
        return equalTo(false);
    }

    private Matcher<? super Boolean> isInvalid() {
        return equalTo(true);
    }

    private HashMap<String, String> reportFor(ValidationMatcher matcher) {
        final ValidationError validationError = new ValidationError();
        matcher.describeTo(validationError);
        final HashMap<String, String> report = new HashMap<String, String>();
        validationError.describeTo(report);
        return report;
    }
}

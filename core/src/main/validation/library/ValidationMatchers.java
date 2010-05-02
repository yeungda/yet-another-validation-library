package validation.library;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class ValidationMatchers {

    public static TypeSafeMatcher<String> isMandatory() {
        final TypeSafeMatcher<String> typeSafeMatcher = afterTrimming(not(equalTo("")));
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return typeSafeMatcher.matches(s);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is mandatory");
            }
        };
    }

    public static TypeSafeMatcher<String> afterTrimming(final Matcher<? super String> matcher) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return matcher.matches(s.trim());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("after trimming ").appendDescriptionOf(matcher);
            }
        };
    }

    public static Matcher<String> isAWholeNumber() {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String string) {
                return string.matches("^[0-9]+$");
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is a whole number");
            }
        };
    }

    public static Matcher<String> hasLength(final Matcher<? super Integer> matcher) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return matcher.matches(s.length());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has length of ").appendDescriptionOf(matcher);
            }
        };
    }

    public static Matcher<String> isADate() {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String value) {
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

            @Override
            public void describeTo(Description description) {
                description.appendText("is a date");
            }
        };
    }
}

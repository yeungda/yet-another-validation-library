package validationhamcrest.library;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ValidationMatchersUnitTest {
    @Test
    public void mandatory() {
        assertThat("x", ValidationMatchers.isMandatory());
        assertThat("", not(ValidationMatchers.isMandatory()));
        assertThat(propertyDescriptionOf(ValidationMatchers.isMandatory()), equalTo("is.mandatory"));
    }

    @Test
    public void wholeNumber() {
        assertThat(forEveryCharacterIn("0123456789"), everyItem(ValidationMatchers.isAWholeNumber()));
        assertThat(forEveryCharacterIn("abc"), everyItem(not(ValidationMatchers.isAWholeNumber())));
        assertThat(propertyDescriptionOf(ValidationMatchers.isAWholeNumber()), equalTo("is.a.whole.number"));
    }


    @Test
    public void lessCharactersThanX() {
        assertThat("", ValidationMatchers.hasLength(lessThan(1)));
        assertThat("1", not(ValidationMatchers.hasLength(lessThan(1))));
        assertThat("1", ValidationMatchers.hasLength(lessThan(2)));
        assertThat(propertyDescriptionOf(ValidationMatchers.hasLength(lessThan(1))), equalTo("has.length.of.a.value.less.than.<1>"));
    }

    @Test
    public void date() {
        assertThat("10/10/2000", ValidationMatchers.isADate());
        assertThat("1/1/2000", ValidationMatchers.isADate());
        assertThat("1/1/10", not(ValidationMatchers.isADate()));
        assertThat("12/13/2000", not(ValidationMatchers.isADate()));
        assertThat("x", not(ValidationMatchers.isADate()));
        assertThat(propertyDescriptionOf(ValidationMatchers.isADate()), equalTo("is.a.date"));
    }

    @Test
    @Ignore("TODO")
    public void dateBetween() {

    }

    @Test
    public void email() {

    }

    @Test
    @Ignore("TODO")
    public void numberBetween() {

    }

    private String propertyDescriptionOf(Matcher<String> typeSafeMatcher) {
        final StringDescription stringDescription = new StringDescription();
        typeSafeMatcher.describeTo(stringDescription);
        final String propertyDescription = stringDescription.toString().trim().replaceAll(" ", ".");
        return propertyDescription;
    }

    private Iterable<String> forEveryCharacterIn(String s) {
        final ArrayList<String> strings = new ArrayList<String>();
        for (char character : s.toCharArray()) {
            strings.add(String.valueOf(character));
        }
        return strings;
    }

}

package validation.core;

import org.hamcrest.Matcher;

public interface Validate {
    public void validateThat(Field field, Matcher<? extends String> matcher);
}

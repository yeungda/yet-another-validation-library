package validationhamcrest.core;

import org.hamcrest.Matcher;

public interface Validate {
    public Validate validateThat(Field field, Matcher<? extends String> matcher);
}

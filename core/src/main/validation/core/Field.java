package validation.core;

import org.hamcrest.Matcher;

public class Field {
    private final String name;
    private final String value;

    public Field(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void describeTo(ValidationError validationError) {
        validationError.setFieldName(name);
    }

    public void describeTo(ErrorId errorId) {
        errorId.setFieldName(name);
    }

    public boolean matches(Matcher<? extends String> matcher) {
        return matcher.matches(value);
    }
}

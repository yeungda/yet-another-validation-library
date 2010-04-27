package validation;

public class Field {
    private final String name;
    private final String value;

    public Field(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public boolean matches(ValidationMatcher validationMatcher) {
        return validationMatcher.passes(value);
    }

    public void describeTo(ValidationError validationError) {
        validationError.setFieldName(name);
    }

    public void describeTo(ErrorId errorId) {
        errorId.setFieldName(name);
    }
}

package validation;

public interface ValidationMatcher {
    boolean passes(String value);

    void describeTo(ValidationError validationError);
}

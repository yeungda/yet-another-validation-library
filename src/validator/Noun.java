package validator;

public class Noun {
    private final Input input;

    public Noun(Input input) {
        this.input = input;
    }

    public void describeTo(Validator validator) {
        validator.validateThat(input, new ValidationMatcher() {});
    }
}

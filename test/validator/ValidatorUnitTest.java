package validator;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class ValidatorUnitTest {
    private Validator validator;

    @Test
    public void validate() {
        new Noun(new Input("noun", "")).describeTo(validator);
        final List<ValidationError> validationErrors = new ArrayList<ValidationError>();
        validator.describeErrors(validationErrors);
        assertThat(validationErrors, hasItem(CoreMatchers.<ValidationError>notNullValue()));
    }

    @Before
    public void start() {
        validator = new Validator();
    }
}

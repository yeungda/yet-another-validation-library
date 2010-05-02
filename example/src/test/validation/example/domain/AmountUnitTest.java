package validation.example.domain;

import org.junit.Test;
import validation.core.Field;
import validation.core.TestingMatchers;
import validation.core.ValidationError;
import validation.core.Validator;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertThat;

public class AmountUnitTest {
    @Test
    public void shouldValidateAmount() {
        final Amount amount = new Amount(new Field("amount", "12345"));
        final Validator validator = new Validator();
        validator.addStates(Arrays.asList(PizzaState.CUSTOMER));
        amount.describeTo(validator);
        final ArrayList<ValidationError> validationErrors = new ArrayList<ValidationError>();
        validator.describeErrors(validationErrors);
        assertThat(validationErrors, TestingMatchers.hasErrorMessageForField("amount", "has length of a value less than or equal to <4>"));
    }
}

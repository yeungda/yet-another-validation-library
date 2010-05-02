package validation.example.domain;

import org.junit.Test;
import validation.core.Field;
import validation.core.Validator;
import validation.core.MapErrorMessageWriter;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class AmountUnitTest {
    @Test
    public void shouldValidateAmount() {
        final Amount amount = new Amount(new Field("amount", "12345"));
        final Validator validator = new Validator();
        validator.addStates(Arrays.asList(PizzaState.CUSTOMER));
        amount.describeTo(validator);
        assertThat(describingErrorsIn(validator), hasEntry("amount", "has length of a value less than or equal to <4>"));
    }

    private HashMap<String, String> describingErrorsIn(Validator validator) {
        final HashMap<String, String> errors = new HashMap<String, String>();
        final MapErrorMessageWriter messageWriter = new MapErrorMessageWriter(errors);
        validator.describeErrorsTo(messageWriter);
        return errors;
    }

}

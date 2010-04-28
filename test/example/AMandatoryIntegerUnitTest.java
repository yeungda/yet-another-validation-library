package example;

import org.junit.Test;
import validation.core.Field;
import validation.core.TestingMatchers;
import validation.core.ValidationError;
import validation.core.Validator;

import java.util.ArrayList;

import static org.junit.Assert.assertThat;

public class AMandatoryIntegerUnitTest {
    @Test
    public void shouldValidateAmount() {

        final AMandatoryInteger aMandatoryInteger = new AMandatoryInteger(new Field("amount", "12345"));
        final Validator validator = new Validator();
        aMandatoryInteger.describeTo(validator);
        final ArrayList<ValidationError> validationErrors = new ArrayList<ValidationError>();
        validator.describeErrors(validationErrors);
        assertThat(validationErrors, TestingMatchers.hasErrorMessageForField("amount", "has length of a value less than or equal to <4>"));
    }
}

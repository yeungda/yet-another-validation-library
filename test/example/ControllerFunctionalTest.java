package example;

import org.junit.Before;
import org.junit.Test;
import validation.core.Field;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

public class ControllerFunctionalTest {
    private Controller controller;

    @Test
    public void validateOurMandatoryInteger() {
        assertThat(validating(new Amount(new Field("amount", ""))), hasEntry("amount", "Please enter an amount"));
        assertThat(validating(new Amount(new Field("amount", "x"))), hasEntry("amount", "Please enter a whole number"));
        assertThat(validating(new Amount(new Field("amount", "9999999999"))), hasEntry("amount", "Please enter less than or equal to 4 digits"));
    }

    private Map<String, String> validating(Amount amount) {
        final Map map = controller.validate(amount);
        final Collection<Map<String, String>> errors = (Collection<Map<String, String>>)map.get("errors");
        final Map<String, String> error = errors.iterator().next();
        return error;
    }

    @Before
    public void start() throws Exception {
        controller = new Controller();
    }
}

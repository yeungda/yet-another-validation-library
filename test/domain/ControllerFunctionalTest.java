package domain;

import org.hamcrest.Matchers;
import org.junit.Test;
import validation.core.Field;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class ControllerFunctionalTest {
    @Test
    public void validateOurMandatoryInteger() {
        final Controller controller = new Controller();
        final Map map = controller.validate(new AMandatoryInteger(new Field("amount", "")));
        final Collection<Map<String, String>> errors = (Collection<Map<String, String>>)map.get("errors");
        assertThat(errors.iterator().next(), Matchers.hasEntry("amount", "Please enter an amount"));
    }
}

package example;

import org.hamcrest.Matchers;
import validation.core.Field;
import validation.core.State;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.*;

public class Purchasee {
    private final Field field;

    public Purchasee(Field field) {
        this.field = field;
    }

    public void describeTo(Collection<? super State> states) {
        if (field.matches(equalTo("customer"))) {
            states.add(PizzaStates.CUSTOMER);
        }
    }
}

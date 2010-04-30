package example;

import org.hamcrest.Matchers;
import validation.core.Field;
import validation.core.Validator;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static validation.library.ValidationMatchers.*;

public class Amount {
    private final Field field;

    public Amount(Field field) {
        this.field = field;
    }

    public void describeTo(Validator validator) {
        validator.whenStates(hasItem(PizzaStates.CUSTOMER)).validateThat(field, isMandatory());
        validator.whenStates(hasItem(PizzaStates.CUSTOMER)).validateThat(field, isAWholeNumber());
        validator.whenStates(hasItem(PizzaStates.CUSTOMER)).validateThat(field, hasLength(lessThanOrEqualTo(4)));
    }
}

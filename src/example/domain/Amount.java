package example.domain;

import validation.core.Field;
import validation.core.Validator;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static validation.library.ValidationMatchers.*;

public class Amount {
    private final Field field;

    public Amount(Field field) {
        this.field = field;
    }

    public void describeTo(Validator validator) {
        validator.whenStates(hasItem(PizzaState.CUSTOMER))
                .validateThat(field, isMandatory())
                .validateThat(field, isAWholeNumber())
                .validateThat(field, hasLength(lessThanOrEqualTo(4)));
    }
}

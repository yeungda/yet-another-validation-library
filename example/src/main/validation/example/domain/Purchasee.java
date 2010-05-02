package validation.example.domain;

import validation.core.Field;

import static org.hamcrest.Matchers.equalTo;

public class Purchasee {
    private final Field field;

    public Purchasee(Field field) {
        this.field = field;
    }

    public void describeTo(States states) {
        if (field.matches(equalTo("customer"))) {
            states.add(PizzaState.CUSTOMER);
        }
    }
}

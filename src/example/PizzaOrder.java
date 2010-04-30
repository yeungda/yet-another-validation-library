package example;

import validation.core.State;
import validation.core.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class PizzaOrder {
    private Amount amount;
    private Purchasee purchasee;

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void describeTo(Validator validator) {
        amount.describeTo(validator);
    }

    public void setPurchasee(Purchasee purchasee) {
        this.purchasee = purchasee;
    }

    public void describeTo(Collection<State> states) {
        purchasee.describeTo(states);
    }
}

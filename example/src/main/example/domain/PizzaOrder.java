package example.domain;

import validationhamcrest.core.Validator;

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

    public void describeTo(States states) {
        purchasee.describeTo(states);
    }
}

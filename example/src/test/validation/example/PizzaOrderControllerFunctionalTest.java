package validation.example;

import org.junit.Before;
import org.junit.Test;
import validation.core.Field;
import validation.example.domain.Amount;
import validation.example.domain.PizzaOrder;
import validation.example.domain.Purchasee;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class PizzaOrderControllerFunctionalTest {
    private PizzaOrderController controller;

    @Test
    public void validatePizzaOrder() {
        assertThat(validating(aStaffOrderWith(new Field("amount", ""))), not(hasEntry("amount", "Please enter an amount")));
        assertThat(validating(aCustomerOrderWith(new Field("amount", ""))), hasEntry("amount", "Please enter an amount"));
        assertThat(validating(aCustomerOrderWith(new Field("amount", "x"))), hasEntry("amount", "Please enter a whole number"));
        assertThat(validating(aCustomerOrderWith(new Field("amount", "9999999999"))), hasEntry("amount", "Please enter less than or equal to 4 digits"));
    }

    private PizzaOrder aStaffOrderWith(Field amountField) {
        final PizzaOrder order = new PizzaOrder();
        order.setPurchasee(new Purchasee(new Field("", "staff")));
        order.setAmount(new Amount(amountField));
        return order;
    }

    private PizzaOrder aCustomerOrderWith(Field amount) {
        final PizzaOrder order = new PizzaOrder();
        order.setPurchasee(new Purchasee(new Field("purchasee", "customer")));
        order.setAmount(new Amount(amount));
        return order;
    }

    private Map<String, String> validating(Amount amount) {
        final PizzaOrder order = new PizzaOrder();
        order.setAmount(amount);
        return validating(order);
    }

    private Map<String, String> validating(PizzaOrder order) {
        final Map map = controller.validate(order);
        final Collection<Map<String, String>> errors = (Collection<Map<String, String>>) map.get("errors");
        final Map<String, String> error = errors.iterator().hasNext() ? errors.iterator().next() : null;
        return error;
    }

    @Before
    public void start() throws Exception {
        controller = new PizzaOrderController();
    }
}

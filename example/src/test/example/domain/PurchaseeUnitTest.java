package example.domain;

import org.hamcrest.Matchers;
import org.junit.Test;
import validationhamcrest.core.Field;

import static org.junit.Assert.assertThat;

public class PurchaseeUnitTest {
    @Test
    public void shouldDescribeCustomerState() {
        final Purchasee purchasee = new Purchasee(new Field("", "customer"));
        final States states = new States();
        purchasee.describeTo(states);
        assertThat(states, Matchers.hasItem(PizzaState.CUSTOMER));
    }

}

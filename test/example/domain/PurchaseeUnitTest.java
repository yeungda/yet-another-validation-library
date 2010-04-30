package example.domain;

import example.domain.PizzaStates;
import example.domain.Purchasee;
import org.hamcrest.Matchers;
import org.junit.Test;
import validation.core.Field;
import validation.core.State;

import java.util.ArrayList;

import static org.junit.Assert.assertThat;

public class PurchaseeUnitTest {
    @Test
    //TODO: it was too hard to create this test
    public void shouldDescribeCustomerState() {
        final Purchasee purchasee = new Purchasee(new Field("", "customer"));
        final ArrayList<? super State> states = new ArrayList<State>();
        purchasee.describeTo(states);
        assertThat(states, Matchers.hasItem(PizzaStates.CUSTOMER));
    }
}

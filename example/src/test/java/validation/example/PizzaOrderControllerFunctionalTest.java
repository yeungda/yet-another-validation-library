/*
 * Copyright 2010 David Yeung
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

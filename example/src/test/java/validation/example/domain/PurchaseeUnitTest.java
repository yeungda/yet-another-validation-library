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

package validation.example.domain;

import org.hamcrest.Matchers;
import org.junit.Test;
import validation.core.Field;
import validation.core.States;

import static org.junit.Assert.assertThat;

public class PurchaseeUnitTest {
    @Test
    public void shouldDescribeCustomerState() {
        final Purchasee purchasee = new Purchasee(new Field("", "customer"));
        final OldStates oldStates = new OldStates();
        final States states = new States();
        purchasee.describeTo(states);
        states.describeApplicableTo(oldStates);
        assertThat(oldStates, Matchers.hasItem(PizzaState.CUSTOMER));
    }

}

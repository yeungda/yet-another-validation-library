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

import validation.core.States;
import validation.core.Validator;

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

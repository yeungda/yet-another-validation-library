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

import org.junit.Test;
import validation.core.Field;
import validation.core.MapErrorMessageWriter;
import validation.core.Validator;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

public class AmountUnitTest {
    @Test
    public void shouldValidateAmount() {
        final Amount amount = new Amount(new Field("amount", "12345"));
        final Validator validator = new Validator();
        validator.addStates(Arrays.asList(PizzaState.CUSTOMER));
        amount.describeTo(validator);
        assertThat(describingErrorsIn(validator), hasEntry("amount", "has length of a value less than or equal to <4>"));
    }

    private HashMap<String, String> describingErrorsIn(Validator validator) {
        final HashMap<String, String> errors = new HashMap<String, String>();
        final MapErrorMessageWriter messageWriter = new MapErrorMessageWriter(errors);
        validator.describeErrorsTo(messageWriter);
        return errors;
    }

}

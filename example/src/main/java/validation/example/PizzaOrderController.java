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

import validation.core.Validator;
import validation.example.domain.PizzaOrder;
import validation.example.domain.States;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PizzaOrderController {
    private final Properties properties;

    public PizzaOrderController() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("validation/example/test.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map validate(PizzaOrder pizzaOrder) {
        final Validator validator = new Validator();
        final States states = new States();
        pizzaOrder.describeTo(states);
        validator.addStates(states);
        pizzaOrder.describeTo(validator);

        final ModelMapErrorMessageWriter errorMessageWriter = new ModelMapErrorMessageWriter(properties);
        validator.describeErrorsTo(errorMessageWriter);
        final ArrayList<Map<String, String>> errorMessages = new ArrayList<Map<String, String>>();
        errorMessageWriter.describeTo(errorMessages);
        final HashMap hashMap = new HashMap();
        hashMap.put("errors", errorMessages);
        return hashMap;
    }

}

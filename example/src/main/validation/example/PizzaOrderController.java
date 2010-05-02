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

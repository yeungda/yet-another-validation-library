package example;

import example.domain.PizzaOrder;
import validation.core.State;
import validation.core.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Controller {
    private final Properties properties;

    public Controller() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("example/test.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map validate(PizzaOrder pizzaOrder) {
        final Validator validator = new Validator();
        final ModelMapErrorMessageWriter errorMessageWriter = new ModelMapErrorMessageWriter(properties);
        final ArrayList<Map<String, String>> errorMessages = new ArrayList<Map<String, String>>();

        final ArrayList<State> states = new ArrayList<State>();
        pizzaOrder.describeTo(states);
        validator.addStates(states);
        
        pizzaOrder.describeTo(validator);

        validator.describeErrorsTo(errorMessageWriter);
        errorMessageWriter.describeTo(errorMessages);
        final HashMap hashMap = new HashMap();
        hashMap.put("errors", errorMessages);
        return hashMap;
    }

}

package validation.example;

import validation.core.ErrorMessageWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ModelMapErrorMessageWriter implements ErrorMessageWriter {
    private final ArrayList<Map<String, String>> errorMessages = new ArrayList<Map<String, String>>();
    private final Properties properties;

    public ModelMapErrorMessageWriter(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void write(String fieldName, String description) {
        final LinkedHashMap<String, String> error = new LinkedHashMap<String, String>();
        final String propertyName = fieldName + ".failed." + description.replaceAll(" ", ".");
        final String message = properties.getProperty(propertyName);
        if (message == null) {
            throw new RuntimeException(String.format("Failed to find property [%s]", propertyName));
        }
        error.put(fieldName, message);
        errorMessages.add(error);
    }

    public void describeTo(Collection<Map<String, String>> errorMessages) {
        errorMessages.addAll(this.errorMessages);
    }
}

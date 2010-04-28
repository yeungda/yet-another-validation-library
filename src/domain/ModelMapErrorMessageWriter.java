package domain;

import validation.core.ErrorMessageWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
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
        error.put(fieldName, (String)properties.get(fieldName + "." + description.replaceAll(" ", ".")));
        errorMessages.add(error);
    }

    public void describeTo(Collection<Map<String,String>> errorMessages) {
        errorMessages.addAll(this.errorMessages);
    }
}

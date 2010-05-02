package validation.core;

import validation.core.ErrorMessageWriter;

import java.util.Map;

public class MapErrorMessageWriter implements ErrorMessageWriter {
    final Map<String, String> validationErrors;

    public MapErrorMessageWriter(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Override
    public void write(String fieldName, String description) {
        validationErrors.put(fieldName, description);
    }
}

package validation.core;

import java.util.Map;

class ValidationError {
    private String fieldName;
    private String description;

    public void describeTo(Map<String, String> report) {
        report.put("fieldName", fieldName);
        report.put("description", description);
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "fieldName='" + fieldName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void describeTo(ErrorMessageWriter errorMessageWriter) {
        errorMessageWriter.write(fieldName, description);
    }
}

package validation.core;

public interface ErrorMessageWriter {
    void write(String fieldName, String description);
}

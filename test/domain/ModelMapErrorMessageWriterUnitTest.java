package domain;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertThat;

public class ModelMapErrorMessageWriterUnitTest {
    @Test
    public void shouldWriteAnErrorMessageToTheModelMap() {
        final Properties properties = new Properties();
        properties.put("error.is.mandatory", "Please enter a description");
        final ModelMapErrorMessageWriter messageWriter = new ModelMapErrorMessageWriter(properties);
        messageWriter.write("error", "is.mandatory");
        assertThat(describing(messageWriter).get(0), Matchers.hasEntry("error", "Please enter a description"));
    }

    private List<Map<String, String>> describing(ModelMapErrorMessageWriter messageWriter) {
        final List<Map<String, String>> errorMessages = new ArrayList<Map<String, String>>();
        messageWriter.describeTo(errorMessages);
        return errorMessages;
    }
}

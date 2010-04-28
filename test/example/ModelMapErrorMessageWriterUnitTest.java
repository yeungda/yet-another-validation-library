package example;

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
        properties.put("username.failed.is.mandatory", "Please enter your User Name");
        final ModelMapErrorMessageWriter messageWriter = new ModelMapErrorMessageWriter(properties);
        messageWriter.write("username", "is.mandatory");
        assertThat(describing(messageWriter).get(0), Matchers.hasEntry("username", "Please enter your User Name"));
    }

    private List<Map<String, String>> describing(ModelMapErrorMessageWriter messageWriter) {
        final List<Map<String, String>> errorMessages = new ArrayList<Map<String, String>>();
        messageWriter.describeTo(errorMessages);
        return errorMessages;
    }
}

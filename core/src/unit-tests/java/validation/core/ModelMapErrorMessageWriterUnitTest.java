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

package validation.core;

import org.junit.Test;

import java.util.HashMap;
import java.util.Properties;

import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

public class ModelMapErrorMessageWriterUnitTest {

    @Test
    public void shouldWriteAnErrorMessageToTheModelMap() {
        final Properties properties = new Properties();
        properties.put("username.failed.is.mandatory", "Please enter your User Name");
        final HashMap<String, String> validationErrors = new HashMap<String, String>();
        final ErrorMessageWriter messageWriter = new MapErrorMessageWriter(validationErrors);
        final MessageFilteringMessageWriter filteringMessageWriter = new MessageFilteringMessageWriter(properties, messageWriter);
        filteringMessageWriter.write("username", "is mandatory");
        assertThat(validationErrors, hasEntry("username", "Please enter your User Name"));
    }

    @Test
    public void shouldThrowExceptionWhenUnableToFindProperty() {
        final Properties emptyProperties = new Properties();
        final MessageFilteringMessageWriter filteringMessageWriter = new MessageFilteringMessageWriter(emptyProperties, null);
        try {
            filteringMessageWriter.write("username", "is invalid");
            fail("expected an exception");
        }
        catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("Failed to find property [username.failed.is.invalid]"));
        }
    }
}

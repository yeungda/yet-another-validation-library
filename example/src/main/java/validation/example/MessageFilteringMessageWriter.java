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

package validation.example;

import validation.core.ErrorMessageWriter;

import java.util.Properties;

public class MessageFilteringMessageWriter implements ErrorMessageWriter {
    private final Properties properties;
    private final ErrorMessageWriter errorMessageWriter;

    public MessageFilteringMessageWriter(Properties properties, ErrorMessageWriter errorMessageWriter) {
        this.properties = properties;
        this.errorMessageWriter = errorMessageWriter;
    }

    @Override
    public void write(String fieldName, String description) {
        this.errorMessageWriter.write(fieldName, lookupMessage(fieldName, description));
    }

    private String lookupMessage(String fieldName, String description) {
        final String propertyName = fieldName + ".failed." + description.replaceAll(" ", ".");
        final String message = properties.getProperty(propertyName);
        if (message == null) {
            throw new RuntimeException(String.format("Failed to find property [%s]", propertyName));
        }
        return message;
    }
}

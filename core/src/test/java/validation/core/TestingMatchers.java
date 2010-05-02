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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.HashMap;

import static org.junit.matchers.JUnitMatchers.hasItem;

public class TestingMatchers {
    public static Matcher<ValidationError> fieldWithError(final String fieldName, final String message) {
        return new TypeSafeMatcher<ValidationError>() {

            @Override
            public boolean matchesSafely(ValidationError validationError) {
                final HashMap<String, String> map = new HashMap<String, String>();
                validationError.describeTo(map);
                return fieldName.equals(map.get("fieldName")) && message.equals(map.get("description"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Field Name ");
                description.appendValue(fieldName);
                description.appendText(" with description ");
                description.appendValue(message);
            }
        };
    }

    public static Matcher<Iterable<ValidationError>> hasErrorMessageForField(String fieldName, String message) {
        return hasItem(fieldWithError(fieldName, message));
    }
}

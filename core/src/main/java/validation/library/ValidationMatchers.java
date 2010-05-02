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

package validation.library;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class ValidationMatchers {

    public static TypeSafeMatcher<String> isMandatory() {
        final TypeSafeMatcher<String> typeSafeMatcher = afterTrimming(not(equalTo("")));
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return typeSafeMatcher.matches(s);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is mandatory");
            }
        };
    }

    public static TypeSafeMatcher<String> afterTrimming(final Matcher<? super String> matcher) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return matcher.matches(s.trim());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("after trimming ").appendDescriptionOf(matcher);
            }
        };
    }

    public static Matcher<String> isAWholeNumber() {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String string) {
                return string.matches("^[0-9]+$");
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is a whole number");
            }
        };
    }

    public static Matcher<String> hasLength(final Matcher<? super Integer> matcher) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return matcher.matches(s.length());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has length of ").appendDescriptionOf(matcher);
            }
        };
    }

    public static Matcher<String> isADate() {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String value) {
                if (!value.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}")) {
                    return false;
                }
                try {
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dateFormat.setLenient(false);
                    dateFormat.parse(value);
                    return true;
                } catch (ParseException e) {
                    return false;
                }

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is a date");
            }
        };
    }
}

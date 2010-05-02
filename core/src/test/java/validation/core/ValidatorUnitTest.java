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
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ValidatorUnitTest {
    private static final Field FIELD = new Field("field", "");
    private Validator validator;

    @Test
    public void shouldValidateNoFields() {
        assertThat(reportOf(new Validator()).values(), hasSize(0));
    }

    @Test
    public void shouldValidateASingleField() {
        assertThat(validating(FIELD, isNeverGoodEnough()), hasEntry("field", "never good enough"));
        assertThat(validating(FIELD, isNeverGoodEnough(), isNeverGoodEnoughForOtherReasons()),
                allOf(
                        hasEntry("field", "never good enough"),
                        not(hasEntry("field", "other reasons"))
                )
        );
        assertThat(validating(FIELD, isAlwaysPerfect(), isNeverGoodEnough()), hasEntry("field", "never good enough"));
        assertThat(validating(FIELD, isAlwaysPerfect()).values(), hasSize(0));
    }

    @Test
    public void shouldValidateMultipleFields() {
        validator.validateThat(new Field("a", ""), isNeverGoodEnough());
        validator.validateThat(new Field("b", ""), isNeverGoodEnough());
        assertThat(reportOf(validator),
                allOf(
                        hasEntry("a", "never good enough"),
                        hasEntry("b", "never good enough")
                )
        );
    }

    @Test
    public void shouldIgnoreValidationThatDependsOnAnUnknownState() {
        validator.addStates(Collections.EMPTY_LIST);
        validator.whenStates(hasItem(TestingStates.BOX_IS_TICKED)).validateThat(new Field("amount", ""), isNeverGoodEnough());
        assertThat(reportOf(validator).values(), Matchers.hasSize(0));
    }

    @Test
    public void shouldApplyValidationThatDependsOnAKnownState() {
        validator.addStates(Arrays.asList(TestingStates.BOX_IS_TICKED));
        validator.whenStates(hasItem(TestingStates.BOX_IS_TICKED)).validateThat(new Field("amount", ""), isNeverGoodEnough());
        assertThat(reportOf(validator).values(), Matchers.hasSize(1));
    }

    @Test
    public void shouldApplyValidationThatDependsOnManyKnownStates() {
        final Validator validator = new Validator();
        validator.addStates(Arrays.asList(TestingStates.BOX_IS_TICKED, TestingStates.SUM_IS_PROVIDED));
        validator.whenStates(allOf(hasItem(TestingStates.BOX_IS_TICKED), hasItem(TestingStates.SUM_IS_PROVIDED))).validateThat(new Field("amount", ""), isNeverGoodEnough());
        assertThat(reportOf(validator).values(), Matchers.hasSize(1));
    }

    @Test
    public void shouldApplyManyValidationRulesThatDependOnAKnownState() {
        validator.addStates(Arrays.asList(TestingStates.BOX_IS_TICKED));
        validator.whenStates(hasItem(TestingStates.BOX_IS_TICKED))
                .validateThat(new Field("amount", ""), isNeverGoodEnough())
                .validateThat(new Field("another", ""), isNeverGoodEnough());
        assertThat(reportOf(validator).values(), Matchers.hasSize(2));
    }

    private Matcher<? extends String> isNeverGoodEnoughForOtherReasons() {
        return isNeverGoodEnoughBecause("other reasons");
    }

    private Matcher<? extends String> isAlwaysPerfect() {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should never happen");
            }
        };
    }

    private Matcher<? extends String> isNeverGoodEnough() {
        return isNeverGoodEnoughBecause("never good enough");
    }

    private TypeSafeMatcher<String> isNeverGoodEnoughBecause(final String reason) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String s) {
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(reason);
            }
        };
    }

    private Map<String, String> validating(Field field, Matcher<? extends String>... matchers) {
        Validator validator = new Validator();
        for (Matcher<? extends String> matcher : matchers) {
            validator.validateThat(field, matcher);
        }
        return reportOf(validator);
    }


    private Map<String, String> reportOf(Validator validator) {
        final HashMap<String, String> errors = new HashMap<String, String>();
        final MapErrorMessageWriter messageWriter = new MapErrorMessageWriter(errors);
        validator.describeErrorsTo(messageWriter);
        return errors;
    }

    @Before
    public void setUp() throws Exception {
        validator = new Validator();
    }

    public enum TestingStates implements State {
        BOX_IS_TICKED, SUM_IS_PROVIDED;
    }
}

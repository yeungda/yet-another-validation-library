package validation.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ValidatorUnitTest {
    private static final Field FIELD = new Field("field", "");
    private Validator validator;

    @Test
    public void shouldValidateNoFields() {
        assertThat(reportOf(new Validator()), hasSize(0));
    }

    @Test
    public void shouldValidateASingleField() {
        assertThat(validating(FIELD, isNeverGoodEnough()), TestingMatchers.hasErrorMessageForField("field", "never good enough"));
        assertThat(validating(FIELD, isNeverGoodEnough(), isNeverGoodEnoughForOtherReasons()),
                allOf(
                        TestingMatchers.hasErrorMessageForField("field", "never good enough"),
                        not(TestingMatchers.hasErrorMessageForField("field", "other reasons"))
                )
        );
        assertThat(validating(FIELD, isAlwaysPerfect(), isNeverGoodEnough()), TestingMatchers.hasErrorMessageForField("field", "never good enough"));
        assertThat(validating(FIELD, isAlwaysPerfect()), hasSize(0));
    }

    @Test
    public void shouldValidateMultipleFields() {
        validator.validateThat(new Field("a", ""), isNeverGoodEnough());
        validator.validateThat(new Field("b", ""), isNeverGoodEnough());
        assertThat(reportOf(validator),
                allOf(
                        TestingMatchers.hasErrorMessageForField("a", "never good enough"),
                        TestingMatchers.hasErrorMessageForField("b", "never good enough")
                )
        );
    }

    @Test
    public void shouldIgnoreValidationThatDependsOnAnUnknownState() {
        validator.addStates(Collections.EMPTY_LIST);
        validator.whenStates(hasItem(TestingStates.BOX_IS_TICKED)).validateThat(new Field("amount", ""), isNeverGoodEnough());
        assertThat(reportOf(validator), Matchers.hasSize(0));
    }

    @Test
    public void shouldApplyValidationThatDependsOnAKnownState() {
        validator.addStates(Arrays.asList(TestingStates.BOX_IS_TICKED));
        validator.whenStates(hasItem(TestingStates.BOX_IS_TICKED)).validateThat(new Field("amount", ""), isNeverGoodEnough());
        assertThat(reportOf(validator), Matchers.hasSize(1));
    }

    @Test
    public void shouldApplyValidationThatDependsOnManyKnownStates() {
        final Validator validator = new Validator();
        validator.addStates(Arrays.asList(TestingStates.BOX_IS_TICKED, TestingStates.SUM_IS_PROVIDED));
        validator.whenStates(allOf(hasItem(TestingStates.BOX_IS_TICKED), hasItem(TestingStates.SUM_IS_PROVIDED))).validateThat(new Field("amount", ""), isNeverGoodEnough());
        assertThat(reportOf(validator), Matchers.hasSize(1));
    }

    @Test
    public void shouldApplyManyValidationRulesThatDependOnAKnownState() {
        validator.addStates(Arrays.asList(TestingStates.BOX_IS_TICKED));
        validator.whenStates(hasItem(TestingStates.BOX_IS_TICKED))
                .validateThat(new Field("amount", ""), isNeverGoodEnough())
                .validateThat(new Field("another", ""), isNeverGoodEnough());
        assertThat(reportOf(validator), Matchers.hasSize(2));
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

    private Collection<ValidationError> validating(Field field, Matcher<? extends String>... matchers) {
        Validator validator = new Validator();
        for (Matcher<? extends String> matcher : matchers) {
            validator.validateThat(field, matcher);
        }
        return reportOf(validator);
    }

    private Collection<ValidationError> reportOf(Validator validator) {
        final List<ValidationError> validationErrors = new ArrayList<ValidationError>();
        validator.describeErrors(validationErrors);
        return validationErrors;
    }

    @Before
    public void setUp() throws Exception {
        validator = new Validator();
    }

    public enum TestingStates implements State {
        BOX_IS_TICKED, SUM_IS_PROVIDED;
    }
}

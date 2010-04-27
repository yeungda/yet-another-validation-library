package domain;

import validation.Field;
import validation.ValidationError;
import validation.ValidationMatcher;
import validation.Validator;

import static domain.ValidationMatchers.*;
import static domain.ValidationMatchers.*;

public class AMandatoryInteger {
    private final Field field;

    public AMandatoryInteger(Field field) {
        this.field = field;
    }

    public void describeTo(Validator validator) {
        validator.validateThat(field, isMandatory());
        validator.validateThat(field, isAWholeNumber());
        validator.validateThat(field, hasLessCharactersThan(30));
    }
}

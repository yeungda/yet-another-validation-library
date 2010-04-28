package domain;

import validation.core.Field;
import validation.core.Validator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class Controller {
    public void validate() {
        final AMandatoryInteger amount = new AMandatoryInteger(new Field("amount", "10"));
        final Validator validator = new Validator();
        amount.describeTo(validator);
        final ModelMapErrorMessageWriter writer = new ModelMapErrorMessageWriter(new Properties());
        validator.describeErrors(writer);
        final ArrayList<Map<String, String>> mapArrayList = new ArrayList<Map<String, String>>();
        writer.describeTo(mapArrayList);
    }

}

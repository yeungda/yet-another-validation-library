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

package validation.example.domain;

import validation.core.States;
import validation.core.Validator;
import validation.library.ValidationMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class Login {
    private Field userName;
    private Field password;

    public void setUserName(Field userName) {
        this.userName = userName;
    }

    public void setPassword(Field password) {
        this.password = password;
    }

    public void describeTo(Validator validator) {
        validator.whenApplicableStates(hasItem(State.AUTHENTICATED)).validateThat(password, ValidationMatchers.isMandatory());
    }

    public void describeTo(States states) {
        states.add(State.AUTHENTICATED).when(userName, not(equalTo("anonymous coward")));
    }

    public static enum State implements validation.core.State {
        AUTHENTICATED, CUSTOMER
    }
}

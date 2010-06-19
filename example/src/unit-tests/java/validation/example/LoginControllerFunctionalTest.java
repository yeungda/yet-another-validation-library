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

import org.junit.Before;
import org.junit.Test;
import validation.example.domain.Field;
import validation.example.domain.Login;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class LoginControllerFunctionalTest {
    private LoginController controller;

    @Test
    public void validateLogin() {
        assertThat(validating(aLoginOf("anonymous coward", "")), not(hasEntry("password", "Please enter your password")));
        assertThat(validating(aLoginOf("lwall", "")), hasEntry("password", "Please enter your password"));
        assertThat(validating(aLoginOf("lwall", "abcdEFGH1")), not(hasEntry("password", "Please enter your password")));
    }

    private Login aLoginOf(String userName, String password) {
        final Login login = new Login();
        login.setUserName(new Field("userName", userName));
        login.setPassword(new Field("password", password));
        return login;
    }

    private Map<? extends String, ? extends String> validating(Login login) {
        final Map map = controller.validate(login);
        final Collection<Map<String, String>> errors = (Collection<Map<String, String>>) map.get("errors");
        final Map<String, String> error = errors.iterator().hasNext() ? errors.iterator().next() : null;
        return error;
    }

    @Before
    public void start() throws Exception {
        controller = new LoginController();
    }

}

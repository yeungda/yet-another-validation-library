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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMapErrorMessageWriter implements ErrorMessageWriter {
    private final ArrayList<Map<String, String>> errorMessages = new ArrayList<Map<String, String>>();

    @Override
    public void write(String fieldName, String description) {
        final LinkedHashMap<String, String> error = new LinkedHashMap<String, String>();
        error.put(fieldName, description);
        errorMessages.add(error);
    }

    public void describeTo(Collection<Map<String, String>> errorMessages) {
        errorMessages.addAll(this.errorMessages);
    }

}

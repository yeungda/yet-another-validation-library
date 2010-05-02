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

import java.util.Map;

class ValidationError {
    private String fieldName;
    private String description;

    public void describeTo(Map<String, String> report) {
        report.put("fieldName", fieldName);
        report.put("description", description);
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "fieldName='" + fieldName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void describeTo(ErrorMessageWriter errorMessageWriter) {
        errorMessageWriter.write(fieldName, description);
    }
}

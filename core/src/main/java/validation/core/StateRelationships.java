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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StateRelationships {
    private ArrayList<Dependency> dependencies;

    public StateRelationships() {
        dependencies = new ArrayList<Dependency>();
    }

    public <T> void resolveInto(List<T> potentialStates, List<T> activeStates) {
        for (Dependency dependency : dependencies) {
            dependency.resolve(potentialStates, activeStates);
        }
    }

    public void has(State state) {
        dependencies.add(new Dependency(state));
    }

    public void has(State state, Dependency... dependency) {
        dependencies.add(new Dependency(state, dependency));
    }

    public static Dependency dependedOnBy(State state) {
        return new Dependency(state);
    }

    public static Dependency dependedOnBy(State state, Dependency dependency) {
        return new Dependency(state, dependency);
    }
}

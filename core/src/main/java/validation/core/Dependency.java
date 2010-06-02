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

import java.util.List;

public class Dependency {
    private State state;
    private final Dependency[] dependencies;

    public Dependency(State state) {
        this(state, new Dependency[0]);
    }

    public Dependency(State state, Dependency... dependencies) {
        this.state = state;
        this.dependencies = dependencies;
    }

    public State getState() {
        return state;
    }

    public <T> void resolve(List<T> potentialStates, List<T> activeStates) {
        if (potentialStates.contains(getState())) {
            activeStates.add((T) getState());
            for (Dependency dependency : dependencies) {
                dependency.resolve(potentialStates, activeStates);
            }
        }
    }
}

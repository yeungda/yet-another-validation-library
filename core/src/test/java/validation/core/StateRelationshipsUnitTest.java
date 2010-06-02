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

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static validation.core.StateRelationships.dependedOnBy;
import static validation.core.TestingStates.*;

public class StateRelationshipsUnitTest {

    @Test
    public void shouldResolveNothingWhenNoStatesAreProvided() {
        assertThat(resolving(new StateRelationships()), Matchers.<TestingStates>emptyIterable());
    }

    @Test
    public void shouldResolveAStateWithNoDependenciesAsActive() {
        final StateRelationships relationships = new StateRelationships();
        relationships.has(A);

        assertThat(resolving(relationships, A), Matchers.contains(A));
    }

    @Test
    public void shouldNotResolveAStateWithUnsatisfiedDependencies() {
        final StateRelationships relationships = new StateRelationships();
        relationships.has(A,
                dependedOnBy(B));

        assertThat(resolving(relationships, B), Matchers.<TestingStates>emptyIterable());
    }

    @Test
    public void shouldResolveAStateThatIsDependentOnAnotherState() {
        final StateRelationships relationships = new StateRelationships();
        relationships.has(A,
                dependedOnBy(B));

        assertThat(resolving(relationships, A, B), Matchers.contains(A, B));
    }

    @Test
    public void shouldResolveAStateThatIsThreeLevelsDeep() {
        final StateRelationships relationships = new StateRelationships();
        relationships.has(A,
                dependedOnBy(B,
                        dependedOnBy(C)));

        assertThat(resolving(relationships, A, B, C), Matchers.contains(A, B, C));
    }

    @Test
    public void shouldNotResolveAStateThatIsThreeLevelsDeepWhenMiddleStateIsMissing() {
        final StateRelationships relationships = new StateRelationships();
        relationships.has(A,
                dependedOnBy(B,
                        dependedOnBy(C)));

        assertThat(resolving(relationships, A, C), Matchers.contains(A));
    }

    @Test
    public void shouldAllowAStateToHaveManyDependencies() {
        final StateRelationships stateGraph = new StateRelationships();
        stateGraph.has(A,
                dependedOnBy(B),
                dependedOnBy(C));

        assertThat(resolving(stateGraph, C, A, B), Matchers.contains(A, B, C));
    }

    private ArrayList<TestingStates> resolving(StateRelationships relationships, TestingStates... potentialStates) {
        final ArrayList<TestingStates> activeStates = new ArrayList<TestingStates>();
        relationships.resolveInto(Arrays.asList(potentialStates), activeStates);
        return activeStates;
    }

    public static class StateGraphUnitTest {
        private StateGraph stateGraph;

        @Test
        public void notResolveUnknownState() {
            assertThat(resolvingApplicableStatesFor(stateGraph), Matchers.<State>emptyIterable());
        }

        @Test
        public void resolveNoDependency() {
            stateGraph.has(A);
            assertThat(resolvingApplicableStatesFor(stateGraph), Matchers.<State>containsInAnyOrder(A));
        }

        @Test
        public void notResolveUnsatisfiedSingleDependency() {
            stateGraph.has(A).dependsOn(B);
            assertThat(resolvingApplicableStatesFor(stateGraph), Matchers.<State>emptyIterable());
        }

        @Test
        public void resolveSingleDependency() {
            stateGraph.has(A).dependsOn(B);
            stateGraph.has(B);
            assertThat(resolvingApplicableStatesFor(stateGraph), Matchers.<State>containsInAnyOrder(A, B));
        }

        private ArrayList<State> resolvingApplicableStatesFor(StateGraph stateGraph) {
            final ArrayList<State> list = new ArrayList<State>();
            stateGraph.resolveApplicable(list);
            return list;
        }

        @Before
        public void setUp() throws Exception {
            stateGraph = new StateGraph();
        }
    }

}

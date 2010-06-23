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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static validation.core.TestingStates.*;

public class StateGraphUnitTest {
    private States states;

    @Test
    public void notResolveUnknownState() {
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>emptyIterable());
    }

    @Test
    public void resolveNoDependency() {
        states.add(A);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(A));
    }

    @Test
    public void notResolveUnsatisfiedSingleDependency() {
        states.add(A).dependsOn(B);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>emptyIterable());
    }

    @Test
    public void resolveSingleDependency() {
        states.add(A).dependsOn(B);
        states.add(B);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(A, B));
    }

    @Test
    public void resolveTransitiveDependency() {
        states.add(A).dependsOn(B);
        states.add(B).dependsOn(C);
        states.add(C);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(A, B, C));
    }

    @Test
    public void notResolveUnmetTransitiveDependency() {
        states.add(A).dependsOn(B);
        states.add(B).dependsOn(C);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>emptyIterable());
    }

    @Test
    public void shouldResolveWhenFieldMatches() {
        states.add(A).when(new Field("", "x"), equalTo("x"));
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>contains(A));
    }

    @Test
    public void notResolveWhenFieldIsNotMatch() {
        states.add(A).when(anyField(), not(Matchers.<String>anything()));
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>emptyIterable());
    }

    @Test
    public void resolveSingleDependencyWithPassingConditions() {
        states.add(A).dependsOn(B);
        states.add(B).when(anyField(), Matchers.<String>anything());
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(A, B));
    }

    @Test
    public void notResolveSingleDependencyWithFailingCondition() {
        states.add(A).dependsOn(B);
        states.add(B).when(anyField(), not(Matchers.<String>anything()));
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>emptyIterable());
    }

    @Test
    public void notResolveTransitiveDependencyWithFailingCondition() {
        states.add(A).dependsOn(B);
        states.add(B).dependsOn(C);
        states.add(C).when(anyField(), not(Matchers.<String>anything()));
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>emptyIterable());
    }

    @Test
    public void resolveTransitiveDependencyWithPassingCondition() {
        states.add(A).dependsOn(B);
        states.add(B).dependsOn(C);
        states.add(C).when(anyField(), Matchers.<String>anything());
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(A, B, C));
    }

    @Test
    public void resolveNoDependencyAndNotResolveSingleDependencyWithConditionalFailure() {
        states.add(C);
        states.add(A).dependsOn(B);
        states.add(B).when(anyField(), not(Matchers.<String>anything()));
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(C));
    }

    @Test
    public void resolveDoubleDependency() {
        states.add(A).dependsOn(B).dependsOn(C);
        states.add(B);
        states.add(C);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(A, B, C));
    }

    @Test
    public void notResolveDoubleDependencyWhenOneIsMissing() {
        states.add(A).dependsOn(B).dependsOn(C);
        states.add(B);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(B));
    }

    @Test
    public void notResolveDoubleDependencyWhenOneFailsCondition() {
        states.add(A).dependsOn(B).dependsOn(C);
        states.add(B);
        states.add(C).when(anyField(), not(Matchers.<String>anything()));
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(B));
    }

    @Test
    public void resolveSomethingComplicatedTwice() {
        states.add(A).dependsOn(B).dependsOn(C);
        states.add(B).when(anyField(), Matchers.<String>anything());
        states.add(C);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(A, B, C));
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>containsInAnyOrder(A, B, C));
    }

    @Test
    public void shouldNotResolveElementsOfCycleIsDescribed() {
        states.add(A).dependsOn(B);
        states.add(B).dependsOn(A);
        states.add(C);
        assertThat(resolvingApplicableStatesFor(states), Matchers.<State>contains(C));
    }

    private DescribableField anyField() {
        return new Field("", "");
    }

    private ArrayList<State> resolvingApplicableStatesFor(States states) {
        final ArrayList<State> list = new ArrayList<State>();
        states.describeApplicableTo(list);
        return list;
    }

    @Before
    public void setUp() throws Exception {
        states = new States();
    }
}

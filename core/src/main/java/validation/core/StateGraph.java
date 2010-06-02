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

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StateGraph {
    private Set<State> nodes = new HashSet<State>();
    private Set<State> nodesWithIncomingEdges = new HashSet<State>();
    private Map<State, Set<State>> edges = new HashMap<State, Set<State>>();


    public AStateThat has(State state) {
        this.nodes.add(state);
        return new AStateThat(state);
    }

    public void resolveApplicable(List<State> resolvedStates) {
        ArrayList<State> sortedNodes = new ArrayList<State>();
        topologicalSort(sortedNodes);
        for (State sortedNode : sortedNodes) {
            if (nodes.contains(sortedNode) && resolvedStates.containsAll(getEdges(sortedNode))) {
                resolvedStates.add(sortedNode);
            }
        }
    }

    private void topologicalSort(ArrayList<State> sortedNodes) {
        final HashSet<State> nodesWithNoIncomingEdges = new HashSet<State>();
        nodesWithNoIncomingEdges.addAll(nodes);
        nodesWithNoIncomingEdges.removeAll(nodesWithIncomingEdges);
        final ArrayList<State> visited = new ArrayList<State>();
        for (State nodeWithNoIncomingEdges : nodesWithNoIncomingEdges) {
            visit(nodeWithNoIncomingEdges, visited, sortedNodes);
        }
    }

    private void visit(State node, ArrayList<State> visited, ArrayList<State> sortedNodes) {
        if (!visited.contains(node)) {
            visited.add(node);
            for (State state : getEdges(node)) {
                visit(state, visited, sortedNodes);
            }
            sortedNodes.add(node);
        }
    }

    public class AStateThat {
        private final State state;

        public AStateThat(State state) {
            this.state = state;
        }

        public AStateThat dependsOn(State dependency) {
            final Set<State> edgesForNode = getEdges(state);
            edgesForNode.add(dependency);
            edges.put(this.state, edgesForNode);

            nodesWithIncomingEdges.add(dependency);
            return this;
        }

        public AStateThat when(Field field, Matcher<String> stringMatcher) {
            return this;
        }
    }

    private Set<State> getEdges(State state) {
        return edges.get(state) != null ? edges.get(state) : new HashSet<State>();
    }
}

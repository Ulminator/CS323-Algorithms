/**
 * Copyright 2014, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package graph.span;


import graph.Graph;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public interface MSTAlgorithm
{
    /**
     * @param graph a graph containing at least one spanning tree.
     * @return a minimum spanning tree.
     */
    public SpanningTree getMinimumSpanningTree(Graph graph);
}

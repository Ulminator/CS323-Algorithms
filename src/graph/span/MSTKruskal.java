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

import graph.Edge;
import graph.Graph;

import java.util.PriorityQueue;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class MSTKruskal implements MSTAlgorithm
{
    @Override
    public SpanningTree getMinimumSpanningTree(Graph graph)
    {
        DisjointSet forest = new DisjointSet(graph.size());
        PriorityQueue<Edge> queue = createEdgePQ(graph);
        SpanningTree tree = new SpanningTree();
        Edge edge;
        
        while (!queue.isEmpty())
        {
            edge = queue.poll();
            
            if (!forest.inSameSet(edge.getTarget(), edge.getSource()))
            {
                tree.addEdge(edge);
                
                // a spanning tree is found
                if (tree.size()+1 == graph.size()) break;
                // merge forests
                forest.union(edge.getTarget(), edge.getSource());
            }
        }
        
        return tree;
    }
    
    /**
     * @param graph Graph
     * @return PriorityQueue that contains all edges in graph sorted by their weights
     */
    private PriorityQueue<Edge> createEdgePQ(Graph graph)
    {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
		queue.addAll(graph.getAllEdges());
        return queue;
    }
}

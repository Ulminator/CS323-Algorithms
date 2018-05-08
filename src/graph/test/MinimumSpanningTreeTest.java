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
package graph.test;


import graph.Graph;
import graph.span.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class MinimumSpanningTreeTest
{
    @Test
//    @Ignore
    public void testUndirected() throws Exception
    {
//        Scanner scanner = new Scanner(new FileInputStream("/Users/jdchoi/Emory/courses/CS323/tinyEWG.txt"));
//        Scanner scanner = new Scanner(new FileInputStream("/Users/jdchoi/Emory/courses/CS323/mediumEWG.txt"));
//        int V = scanner.nextInt(), E = scanner.nextInt();
//        int source, target;
//        double weight;
//        
//        Graph graph = new Graph(V);
//
//        for (int i=0; i<E; i++)
//        {
//            source = scanner.nextInt();
//            target = scanner.nextInt();
//            weight = scanner.nextDouble();
//            graph.setUndirectedEdge(source, target, weight);
//        }
        
//        Graph graph = new Graph(5);
//        graph.setUndirectedEdge(0, 1, 5-2);
//        graph.setUndirectedEdge(0, 2, 5-1);
//        graph.setUndirectedEdge(0, 3, 5-4);
//        graph.setUndirectedEdge(0, 4, 5-3);
//        graph.setUndirectedEdge(1, 2, 5-3);
//        graph.setUndirectedEdge(2, 3, 5-1);
//        graph.setUndirectedEdge(3, 4, 5-2);
        Graph graph = new Graph(4);
        graph.setUndirectedEdge(0, 1, 2);
        graph.setUndirectedEdge(0, 2, 8);
        graph.setUndirectedEdge(0, 3, 2);
        graph.setUndirectedEdge(1, 3, 1);
        graph.setUndirectedEdge(1, 2, 3);
        graph.setUndirectedEdge(2, 3, 4);

        Graph g2 = new Graph(6);
        g2.setUndirectedEdge(0, 1, 1);
        g2.setUndirectedEdge(0, 2, 4);
        g2.setUndirectedEdge(0, 3, 3);
        g2.setUndirectedEdge(1, 3, 2);
        g2.setUndirectedEdge(2, 3, 4);
        g2.setUndirectedEdge(1, 2, 4);
        g2.setUndirectedEdge(3, 4, 4);
        g2.setUndirectedEdge(3, 5, 7);
        g2.setUndirectedEdge(4, 5, 5);

        Graph g3 = new Graph(1);
        g3.setUndirectedEdge(0,0,10);
        g3.setUndirectedEdge(0,0,5);

        Graph g4 = new Graph(4);
        g4.setUndirectedEdge(0, 1, 20);
        g4.setUndirectedEdge(2, 3, 10);

//        MSTAlgorithm prim = new MSTPrim();
//        MSTAlgorithm kruskal = new MSTKruskal();
//        SpanningTree tree;
//
//        tree = prim.getMinimumSpanningTree(graph);
//        System.out.println(tree);
//        System.out.println(tree.getTotalWeight());
//
//        tree = kruskal.getMinimumSpanningTree(graph);
//        System.out.println(tree);
//        System.out.println(tree.getTotalWeight());

//        MSTAll ulmer = new MSTUlmer();
        MSTAll ulmer = new MSTUlmerPrim();
//        MSTAll ulmer = new MSTUlmerKruskal();

//        System.out.println("Ulmer Test");
        List<SpanningTree> tests = ulmer.getMinimumSpanningTrees(graph);
        System.out.println("----------Result----------");
        System.out.println(tests);
        System.out.println("Number of Spanning Trees: " + tests.size());

        List<SpanningTree> tests2 = ulmer.getMinimumSpanningTrees(g2);
        System.out.println("----------Result----------");
        System.out.println(tests2);
        System.out.println("Number of Spanning Trees: " + tests2.size());

        List<SpanningTree> tests3 = ulmer.getMinimumSpanningTrees(g3);
        System.out.println("----------Result----------");
        System.out.println(tests3);
        System.out.println("Number of Spanning Trees: " + tests3.size());

        List<SpanningTree> tests4 = ulmer.getMinimumSpanningTrees(g4);
        System.out.println("----------Result----------");
        System.out.println(tests4);
        System.out.println("Number of Spanning Trees: " + tests4.size());
    }
    
    @Test
    @Ignore
    public void testDirected()
    {
        Graph graph = new Graph(5);
        
        graph.setDirectedEdge(0, 1, 20);
        graph.setDirectedEdge(2, 1,  5);
        graph.setDirectedEdge(3, 1,  2);
        graph.setDirectedEdge(4, 1,  5);

        graph.setDirectedEdge(0, 2, 15);
        graph.setDirectedEdge(1, 2, 28);
        graph.setDirectedEdge(3, 2,  4);
        graph.setDirectedEdge(4, 2,  7);

        graph.setDirectedEdge(0, 3,  4);
        graph.setDirectedEdge(1, 3,  3);
        graph.setDirectedEdge(2, 3,  8);
        graph.setDirectedEdge(4, 3, 30);

        graph.setDirectedEdge(0, 4, 10);
        graph.setDirectedEdge(1, 4, 12);
        graph.setDirectedEdge(2, 4,  6);
        graph.setDirectedEdge(4, 4, 20);
        
        MSTEdmonds mst = new MSTEdmonds();
        SpanningTree tree = mst.getMinimumSpanningTree(graph);
        
        graph = new Graph(4);
        
        graph.setDirectedEdge(0, 1, 4);
        graph.setDirectedEdge(0, 2, 4);
        graph.setDirectedEdge(0, 3, 4);
        graph.setDirectedEdge(1, 3, 1);
        graph.setDirectedEdge(2, 1, 2);
        graph.setDirectedEdge(3, 2, 3);
        
        tree = mst.getMinimumSpanningTree(graph);
        System.out.println(tree.toString());
    }
}

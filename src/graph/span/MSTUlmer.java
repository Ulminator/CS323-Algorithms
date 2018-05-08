package graph.span;

import graph.Graph;
import graph.Edge;

import graph.span.MSTPrim;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

public class MSTUlmer implements MSTAll {

//    private List<SpanningTree> trees = new ArrayList<>();
    private double minWeight;

    /**
     * @param graph an undirected graph containing zero to many spanning trees.
     * @return list of all minimum spanning trees.
     */
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {

        this.minWeight = 0.0;

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        SpanningTree tree = new SpanningTree();
        Set<Integer> visited = new HashSet<>();

        List<SpanningTree> trees = new ArrayList<>(); ////
        List<Edge> edgeList = new ArrayList<>();////

        add(queue, visited, graph, 0);   //Adds all edges that connect to node 0

        if(queue.size()==0){
            return trees;         //This node is not reachable, so bail
        }

        double minEdgeWeight = queue.peek().getWeight();
        while (!queue.isEmpty()){
            if (queue.peek().getWeight()== minEdgeWeight){
                edgeList.add(queue.poll());
            }
            else{ queue.poll(); }
        }
//        queue.addAll(edgeList);
        for (Edge edge: edgeList){
            SpanningTree treeCopy = new SpanningTree(tree);
            treeCopy.addEdge(edge);
            Set<Integer> visitedCopy = new HashSet<>(visited);

            add(queue, visitedCopy, graph, edge.getSource());

            trees.addAll(findTreesAux(treeCopy, queue, visitedCopy, graph));
        }
        return trees;
    }

    private List<SpanningTree> findTreesAux(SpanningTree tree, PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph){
        List<SpanningTree> trees = new ArrayList<>();
        Edge edge;

        while (!queue.isEmpty())
        {
//            System.out.println(queue);
            PriorityQueue<Edge> queueCopy = new PriorityQueue<>(queue);

            edge = queue.poll();

            //check if queue empty, and if next is equal weight
            if (!visited.contains(edge.getSource()))
            {
//                tree.addEdge(edge);
                SpanningTree treeCopy = new SpanningTree(tree);
                treeCopy.addEdge(edge);

//                PriorityQueue<Edge> queueCopy = new PriorityQueue<>(queue);
                Set<Integer> visitedCopy = new HashSet<>(visited);
                add(queueCopy, visitedCopy, graph, edge.getSource());

//                System.out.println("QUEUE");
//                System.out.println(queue);
//                System.out.println("QUEUE Copy");
//                System.out.println(queueCopy);
//                System.out.println("VISITED");
//                System.out.println(visited);
//                System.out.println("VISITED COPY");
//                System.out.println(visitedCopy);
//                System.out.println("Tree");
//                System.out.println(tree);
//                System.out.println("Tree Copy");
//                System.out.println(treeCopy);
//                System.out.println("---findTreeAux Start ---");

                trees.addAll(findTreesAux(treeCopy, queueCopy, visitedCopy, graph));

                if (treeCopy.size()+1 == graph.size()){
//                if (tree.size()+1 == graph.size()){
                    if (minWeight==0.0){
//                        System.out.println("FIRST TREE FOUND");
//                        System.out.println(treeCopy);
//                        trees.add(tree);
                        trees.add(treeCopy);
                        minWeight = treeCopy.getTotalWeight();
//                        minWeight = tree.getTotalWeight();
                    }
//                    else if (tree.getTotalWeight() == minWeight){
                    else if (treeCopy.getTotalWeight() == minWeight){
//                    else if (treeCopy.getTotalWeight() == trees.get(0).getTotalWeight()){
//                        System.out.println(trees.get(0).getTotalWeight());
//                        System.out.println("TREE FOUND");
//                        System.out.println(treeCopy);
//                        trees.add(tree);
                        trees.add(treeCopy);
                    }
                    break;
                }
                add(queue, visited, graph, edge.getSource());
            }
        }
        return trees;
    }


    /**
     * @param queue Queue of all vertices awaited to explore
     * @param visited Set of visited vertices
     * @param graph Graph
     * @param target Target vertex
     */
    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target)
    {
        visited.add(target);

        for (Edge edge : graph.getIncomingEdges(target))
        {
            if (!visited.contains(edge.getSource()))
                queue.add(edge);
        }
    }
}

package graph.span;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class MSTUlmerPrim implements MSTAll {

    /**
     * @param graph an undirected graph containing zero to many spanning trees.
     * @return list of all minimum spanning trees.
     */
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph)
    {
        List<SpanningTree> trees = new ArrayList<>();

        if(graph.getAllEdges().size()==0){
            return trees;
        }

        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        SpanningTree tree = new SpanningTree();

        //Add all connecting vertices from start vertex to the queue
        add(queue, visited, graph, 0);

        findTreesAux(tree, visited, queue, graph, trees);
//        System.out.println("# Trees: " + trees.size());
        return trees;
    }

    private void findTreesAux(SpanningTree tree, Set<Integer> visited, PriorityQueue<Edge> queue, Graph graph, List<SpanningTree> trees){

        Edge edge;

        while (!queue.isEmpty()){
            edge = queue.poll();

            if (queue.size()>0 && queue.peek().getWeight() == edge.getWeight()) {  //If other elements in PQ have same weight, branch

                if (!visited.contains(edge.getSource())) {
                    SpanningTree treeCopy = new SpanningTree(tree); //Copy tree
                    PriorityQueue<Edge> queueCopy = new PriorityQueue<>(queue); //Copy queue
                    Set<Integer> visitedCopy = new HashSet<>(visited);  //Visited set copy

                    treeCopy.addEdge(edge);

                    if (treeCopy.size() + 1 == graph.size()) {
                        verifyTree(treeCopy, trees);
                    }

                    add(queueCopy, visitedCopy, graph, edge.getSource());
                    findTreesAux(treeCopy, visitedCopy, queueCopy, graph, trees);
                }
            }

            else{
                if (!visited.contains(edge.getSource())){
                    tree.addEdge(edge);
                    if (tree.size() + 1 == graph.size()){
                        verifyTree(tree, trees);
                    }
                    add(queue, visited, graph, edge.getSource()); //Might not need
                }
            }
        }
    }

    private void verifyTree(SpanningTree tree, List<SpanningTree> trees){
        if (trees.size()==0){
            trees.add(tree);
        }
        else if (tree.getTotalWeight()==trees.get(0).getTotalWeight()){
            trees.add(tree);
        }
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

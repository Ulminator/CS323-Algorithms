package graph.span;

import graph.Edge;
import graph.Graph;

import java.util.*;

public class MSTUlmerKruskal implements MSTAll {

    /**
     * @param graph an undirected graph containing zero to many spanning trees.
     * @return list of all minimum spanning trees.
     */
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {

        List<SpanningTree> trees = new ArrayList<>();

        if(graph.getAllEdges().size()==0){
            return trees;
        }

        DisjointSet forest = new DisjointSet(graph.size());
        PriorityQueue<Edge> queue = createEdgePQ(graph);
        SpanningTree tree = new SpanningTree();

        Set<String> unique = new HashSet<>();  //Used to check if an MST has already been added to trees.

        findTreesAux(tree, forest, queue, graph, trees, unique);
//        System.out.println("Number of Trees Found: " + trees.size());
        return trees;
    }

    private void findTreesAux(SpanningTree tree, DisjointSet forest, PriorityQueue<Edge> queue, Graph graph, List<SpanningTree> trees, Set<String> unique){

        Edge edge;

        while (!queue.isEmpty())
        {
            edge = queue.poll();

            if (queue.size()>0 && queue.peek().getWeight() == edge.getWeight()) {  //Check if equivalent to next edge in queue

                if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {  //If so, check if edge connects different forests
                    SpanningTree treeCopy = new SpanningTree(tree);  //If so, branch
                    PriorityQueue<Edge> queueCopy = new PriorityQueue<>(queue);
                    DisjointSet forestCopy = new DisjointSet(forest);

                    treeCopy.addEdge(edge);

                    if (treeCopy.size() + 1 == graph.size()) {
                        verifyTree(treeCopy, trees, unique);
                    }

                    forestCopy.union(edge.getTarget(), edge.getSource());
                    findTreesAux(treeCopy, forestCopy, queueCopy, graph, trees, unique);
                }
            }

            else {
                if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {

                    tree.addEdge(edge);
                    if (tree.size() + 1 == graph.size()) {
                        verifyTree(tree, trees, unique);
                    }

                    forest.union(edge.getTarget(), edge.getSource());
                }
            }
        }
    }

    private void verifyTree(SpanningTree tree, List<SpanningTree> trees, Set<String> unique){
        if (trees.size()==0){
            trees.add(tree);
            unique.add(tree.getUndirectedSequence());
        }
        else if (tree.getTotalWeight()==trees.get(0).getTotalWeight()){
            if (!unique.contains(tree.getUndirectedSequence())){
                trees.add(tree);
                unique.add(tree.getUndirectedSequence());
            }
        }
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

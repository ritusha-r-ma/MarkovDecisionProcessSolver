package graph;

import graph.node.ChanceNode;
import graph.node.DecisionNode;
import graph.node.Node;
import graph.node.TerminalNode;

import java.util.*;

/**
 * This class is the most refined form of graph, which will be used to process MDP and backwardInduction
 */
public class Graph {

    HashMap<Node, List<String>> graphMap = new HashMap<>();

    public Graph () {}

    public Graph (HashMap<Node, List<String>> map) {
        graphMap = map;
    }

    public Graph createGraph(Data data) {
        Set<String> totalNodes = data.getTotalNodes();
        Map<String, List<String>> nodeMapping = data.getNodeAdjMap();
        Map<String, List<Double>> probData = data.getProbabilities();
        Map<String, Integer> rewardData = data.getReward();

        for (String node : totalNodes) {
            List<String> adjNodes = nodeMapping.get(node);
            List<Double> prob = probData.get(node);

            if (adjNodes != null && adjNodes.size() != 0 && prob == null) {
                prob = new ArrayList<>();
                prob.add((double) 1);
            }

            // Terminal Node
            if (adjNodes == null || adjNodes.size() == 0) {
                graphMap.put(new TerminalNode(node, rewardData.getOrDefault(node, 0)), new ArrayList<>());
            }

            // Chance node
            else if (adjNodes.size() == prob.size()) {
                graphMap.put(new ChanceNode(node, rewardData.getOrDefault(node, 0), prob), adjNodes);
            }

            // Decision Node
            else {
                graphMap.put(new DecisionNode(node, rewardData.getOrDefault(node, 0), prob), adjNodes);
            }
        }

        return new Graph(graphMap);
    }

    public HashMap<Node, List<String>> getGraphMap() {
        return graphMap;
    }
}

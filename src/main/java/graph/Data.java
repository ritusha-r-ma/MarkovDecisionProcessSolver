package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * This class is responsible for collecting/massage data coming from inputFile
 */
public class Data {
    private HashMap<String, List<String>> nodeAdjMap = new HashMap<>();
    private HashMap<String, Integer> reward = new HashMap<>();
    private HashMap<String, List<Double>> probabilities = new HashMap<>();
    private HashSet<String> totalNodes = new HashSet<>();

    public void addAllNodes(String node, List<String> adjNode) {
        nodeAdjMap.put(node, adjNode);
        totalNodes.add(node);
        adjNode.forEach(c -> totalNodes.add(c.trim()));
    }

    public void addReward(String node, int rewardPoint) {
        reward.put(node, rewardPoint);
    }

    public void addProbability(String node, List<Double> prob) {
        probabilities.put(node, prob);
    }

    public HashMap<String, List<String>> getNodeAdjMap() {
        return nodeAdjMap;
    }

    public HashMap<String, Integer> getReward() {
        return reward;
    }

    public HashMap<String, List<Double>> getProbabilities() {
        return probabilities;
    }

    public HashSet<String> getTotalNodes() {
        return totalNodes;
    }
}

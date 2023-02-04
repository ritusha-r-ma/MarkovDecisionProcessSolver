package validation;

import graph.Data;
import utilities.ErrorHandlingUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * This class will validate the graph formed from the data we got from txt file
 * If this class return true then we're okay to proceed with solver otherwise it will exit
 */
public class GraphValidation {

    public GraphValidation() {
    }

    /**
     * Parse graph, if it is not valid, it will error out
     * otherwise we will proceed
     * @param data
     */
    public boolean isGraphValid(Data data) {
        isProbAcceptable(data.getProbabilities());
        isTerminalNodeAcceptable(data.getTotalNodes(), data.getNodeAdjMap(), data.getProbabilities());
        return true;
    }

    /**
     * Check if probability distribution is correct for every node
     * otherwise error out
     * @param probabilities
     */
    private void isProbAcceptable(HashMap<String, List<Double>> probabilities) {
        for (Map.Entry<String, List<Double>> node : probabilities.entrySet()) {
            double sum = 0;
            for (Double value : node.getValue()) {
                sum += value;
            }

            if (sum > 1.0) {
                ErrorHandlingUtil.errorOccurred("Node " + node.getKey() + " has unequal distribution of probabilities");
            }
        }
    }

    /**
     * This method will check if any terminal node has any probabilities associated with it
     * if yes, then it will error out otherwise we can proceed
     * @param allNodes
     * @param nodeAdjMap
     * @param probabilities
     */
    private void isTerminalNodeAcceptable(HashSet<String> allNodes, HashMap<String, List<String>> nodeAdjMap, HashMap<String, List<Double>> probabilities) {
        for (String node : allNodes) {
            boolean isTerminalNode = !nodeAdjMap.containsKey(node) || nodeAdjMap.get(node).size() == 0;

            if (isTerminalNode && probabilities.containsKey(node)) {
                ErrorHandlingUtil.errorOccurred("Node " + node + " is a terminal node but still has probabilities associated with it");
            }
        }
    }
}

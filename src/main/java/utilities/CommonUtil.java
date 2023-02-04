package utilities;

import graph.Graph;
import graph.node.Node;

import java.util.*;
import java.util.stream.Collectors;

public class CommonUtil {

    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    /**
     * Return true, if graph contains cycle otherwise return false
     * @param graph
     * @return
     */
    public static boolean isCyclicGraph(Graph graph) {
        Map<String, Integer> inDegree = calcIncomingDegree(graph.getGraphMap());
        Queue<String> queue = new LinkedList<>();
        int count = 0;
        for (Map.Entry<String, Integer> node : inDegree.entrySet()) {
            if (node.getValue() == 0) {
                queue.add(node.getKey());
                count++;
            }
        }

        while (!queue.isEmpty()) {
            String parent = queue.poll();

            List<String> adjacentNodes = new ArrayList<>();
            for (Map.Entry<Node, List<String>> node : graph.getGraphMap().entrySet()) {
                if (node.getKey().getLabel().equals(parent)) {
                    adjacentNodes = node.getValue();
                    break;
                }
            }

            for (String child : adjacentNodes) {
                inDegree.put(child, inDegree.get(child) - 1);
                if (inDegree.get(child) == 0) {
                    queue.add(child);
                    count++;
                }
            }
        }

        return count != inDegree.size();
    }

    private static Map<String, Integer> calcIncomingDegree(HashMap<Node, List<String>> graph) {
        Map<String, Integer> incomingDegree = new HashMap<>();
        for (Map.Entry<Node, List<String>> node : graph.entrySet()) {
            incomingDegree.putIfAbsent(node.getKey().getLabel(), 0);

            for (String edgeNode : node.getValue()) {
                if (incomingDegree.containsKey(edgeNode)) {
                    incomingDegree.put(edgeNode, incomingDegree.get(edgeNode) + 1);
                } else {
                    incomingDegree.put(edgeNode, 1);
                }
            }
        }

        return incomingDegree;
    }

    public static String calcRoot(HashMap<Node, List<String>> graph) {
        Map<String, Integer> inDegree = calcIncomingDegree(graph);
        int rootCount = 0;
        String root = null;
        for(String node : inDegree.keySet()){
            if(inDegree.get(node) == 0){
                rootCount++;
                root = node;
            }
        }
        if (Objects.isNull(root)) {
            ErrorHandlingUtil.errorOccurred("No root found");
        } else if(rootCount > 1){
            ErrorHandlingUtil.errorOccurred("Multiple root found");
        }

        return root;
    }

    public static void printOutput(Map<String, String> decisions, Map<String, Double> expectedValue) {
        for (Map.Entry<String, String> decision : decisions.entrySet()) {
            System.out.print(decision.getKey() + " --> " + decision.getValue() + " ");
        }

        System.out.println();

        for (Map.Entry<String, Double> value : expectedValue.entrySet()) {
            System.out.print(value.getKey() + " = " + round(value.getValue(), 3) + " ");
        }
    }
}

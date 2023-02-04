package graph.node;

import utilities.NodeEnum;

import java.util.List;

public class DecisionNode extends Node {

    List<Double> nodeProbability;

    public DecisionNode(String nodeLabel, int nodeReward, List<Double> prob) {
        super(nodeLabel, nodeReward, NodeEnum.DECISION_NODE);
        nodeProbability = prob;
    }

    public List<Double> getNodeProbability() {
        return nodeProbability;
    }
}

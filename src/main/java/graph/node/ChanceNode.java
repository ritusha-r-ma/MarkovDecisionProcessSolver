package graph.node;

import utilities.NodeEnum;

import java.util.List;

public class ChanceNode extends Node {

    List<Double> nodeProbability;

    public ChanceNode(String nodeLabel, int nodeReward, List<Double> probability) {
        super(nodeLabel, nodeReward, NodeEnum.CHANCE_NODE);
        nodeProbability = probability;
    }

    public List<Double> getNodeProbability() {
        return nodeProbability;
    }
}

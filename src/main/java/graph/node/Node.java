package graph.node;

import utilities.NodeEnum;

public class Node {
    String label;
    int reward;
    NodeEnum nodeType;

    public Node(String nodeLabel, int nodeReward, NodeEnum type) {
        label = nodeLabel;
        reward = nodeReward;
        nodeType = type;
    }

    public String getLabel() {
        return label;
    }

    public int getReward() {
        return reward;
    }

    public NodeEnum getNodeType() {
        return nodeType;
    }
}

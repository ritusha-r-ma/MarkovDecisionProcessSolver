package graph.node;

import utilities.NodeEnum;

import java.util.List;

public class TerminalNode extends Node {

    public TerminalNode(String nodeLabel, int nodeReward) {
        super(nodeLabel, nodeReward, NodeEnum.TERMINAL_NODE);
    }
}

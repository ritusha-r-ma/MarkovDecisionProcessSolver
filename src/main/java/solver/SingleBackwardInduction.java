package solver;

import graph.node.ChanceNode;
import graph.node.DecisionNode;
import graph.node.Node;
import utilities.CommonUtil;
import utilities.NodeEnum;
import validation.ArgValidation;

import java.util.*;

public class SingleBackwardInduction {

    private final boolean min;
    private final Map<String, Double> expectedValue = new HashMap<>();
    private final Map<String, String> decisions = new HashMap<>();

    public SingleBackwardInduction() {
        this.min = ArgValidation.getIsMin();
    }

    public void execute(HashMap<Node, List<String>> g) {
        execute(g, CommonUtil.calcRoot(g));
        CommonUtil.printOutput(decisions, expectedValue);
    }

    private void execute(HashMap<Node, List<String>> g, String source){
        if (!Objects.isNull(expectedValue.get(source))) {
            return;
        }

        Node parentNode = null;
        for (Map.Entry<Node, List<String>> node : g.entrySet()) {
            if (node.getKey().getLabel().equals(source)) {
                parentNode = node.getKey();
                break;
            }
        }

        // If node is a terminal node, then direct put reward value
        if (parentNode.getNodeType() == NodeEnum.TERMINAL_NODE) {
            double reward = parentNode.getReward();
            expectedValue.put(source, reward);
        }
        // If node is a chance node, then calculate neighbour expected value
        else if (parentNode.getNodeType() == NodeEnum.CHANCE_NODE) {
            ChanceNode chanceNode = (ChanceNode) parentNode;
            List<String> neighbour = g.get(parentNode);

            double neighbourValue = 0d;

            neighbourValue = neighbourValue + parentNode.getReward();

            for (int i=0; i<neighbour.size(); i++) {
                execute(g, neighbour.get(i));
                neighbourValue = neighbourValue + expectedValue.get(neighbour.get(i))*chanceNode.getNodeProbability().get(i);
            }

            expectedValue.put(source, neighbourValue);
        } else {
            DecisionNode decisionNode = (DecisionNode) parentNode;
            List<String> neighbour = g.get(parentNode);

            if (Objects.isNull(expectedValue.get(source))) {
                expectedValue.put(source, min? Double.MAX_VALUE : Double.MIN_VALUE);
            }

            double givenProb = decisionNode.getNodeProbability().get(0);
            double derivedProb = givenProb == 1 ? 0: (1 - givenProb)/(neighbour.size() - 1);

            for (String value : neighbour) {
                execute(g, value);
            }

            for(int i = 0; i < neighbour.size(); i++) {
                for(int j = 0; j<neighbour.size(); j++) {
                    double neighbourValue = expectedValue.get(neighbour.get(j));
                    neighbourValue = neighbourValue + parentNode.getReward();

                    if (i == j) {
                        updateDecision(source, neighbour, givenProb, j, neighbourValue);
                    } else {
                        updateDecision(source, neighbour, derivedProb, j, neighbourValue);
                    }
                }
            }
        }
    }

    private void updateDecision(String source, List<String> neighbour, double prob, int j, double value) {
        if(min) {
            if (expectedValue.get(source) > value*prob) {
                decisions.put(source, neighbour.get(j));
                expectedValue.put(source, value*prob);
            }
        } else {
            if (expectedValue.get(source) < value*prob) {
                decisions.put(source, neighbour.get(j));
                expectedValue.put(source, value*prob);
            }
        }
    }
}

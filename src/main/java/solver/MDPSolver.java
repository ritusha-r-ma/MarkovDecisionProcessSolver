package solver;

import graph.Graph;
import graph.node.ChanceNode;
import graph.node.DecisionNode;
import graph.node.Node;
import utilities.CommonUtil;
import utilities.NodeEnum;
import validation.ArgValidation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MDPSolver {

    private final int iter;
    private final boolean isMin;
    private final double tol;
    private final double discFactor;

    private HashMap<String, Double> value;
    private final HashMap<String, String> policy;

    public MDPSolver() {
        this.iter = ArgValidation.getIteration();
        this.isMin = ArgValidation.getIsMin();
        this.tol = ArgValidation.getTolerance();
        this.discFactor = ArgValidation.getDiscountFactor();
        this.value = new HashMap<>();
        this.policy = new HashMap<>();
    }

    public void applyMDP(HashMap<Node, List<String>> graph) {
        boolean endExecution = false;

        for (Node n : graph.keySet()) {
            value.put(n.getLabel(), (double) n.getReward());
        }

        while (!endExecution) {
            Map<String, String> policy = (Map<String, String>) this.policy.clone();

            for (Map.Entry<Node, List<String>> node : graph.entrySet()){
                if (node.getKey().getNodeType() == NodeEnum.DECISION_NODE) {
                    List<String> neighbour = node.getValue();
                    String dest = this.policy.getOrDefault(node.getKey().getLabel(), neighbour.get(0));

                    for (String adjNode : neighbour) {
                        if (isMin && value.get(dest) > value.get(adjNode)) {
                            dest = adjNode;
                        }

                        if(!isMin && value.get(dest) < value.get(adjNode)) {
                            dest = adjNode;
                        }
                    }

                    this.policy.put(node.getKey().getLabel(), dest);
                }
            }

            valueIterationSolver(graph);
            endExecution = updateExecutionBoolean(policy);
        }

        CommonUtil.printOutput(policy, value);
    }

    private boolean updateExecutionBoolean(Map<String, String> policy) {
        if (this.policy.isEmpty()) {
            return true;
        }

        for (String s : this.policy.keySet()) {
            if (!Objects.nonNull(policy.get(s)) || !this.policy.get(s).equals(policy.get(s))) {
                return false;
            }
        }

        return true;
    }

    private void valueIterationSolver(HashMap<Node, List<String>> graph) {
        int iteration = 0;
        boolean reachedTolerance = false;

        while (iteration < iter && !reachedTolerance) {
            HashMap<String, Double> updatedValue = (HashMap<String, Double>) value.clone();

            for (Map.Entry<Node, List<String>> node : graph.entrySet()) {
                List<String> neighbour = node.getValue();
                double reward = node.getKey().getReward();

                // If node is a chance node
                if (node.getKey().getNodeType() == NodeEnum.CHANCE_NODE) {
                    ChanceNode chanceNode = (ChanceNode) node.getKey();

                    for (int i = 0; i < neighbour.size(); i++) {
                        reward = reward + discFactor * chanceNode.getNodeProbability().get(i) * this.value.get(neighbour.get(i));
                    }
                }

                // If node is decision node
                else if (node.getKey().getNodeType() == NodeEnum.DECISION_NODE) {
                    DecisionNode decisionNode = (DecisionNode) node.getKey();

                    double givenProb = decisionNode.getNodeProbability().get(0);
                    double derivedProb = (1 - givenProb) / (neighbour.size() - 1);

                    reward = reward + discFactor * givenProb * this.value.get(policy.get(node.getKey().getLabel()));

                    for (String s : neighbour) {
                        if (!s.equals(policy.get(node.getKey().getLabel()))) {
                            reward = reward + discFactor * derivedProb * this.value.get(s);
                        }
                    }
                }

                updatedValue.put(node.getKey().getLabel(), reward);
            }

            reachedTolerance = isToleranceReached(updatedValue);
            this.value = updatedValue;
            iteration++;
        }
    }

    private boolean isToleranceReached(HashMap<String, Double> updatedValue) {
        for (String key : updatedValue.keySet()) {
            if (Math.abs(updatedValue.get(key) - value.get(key)) > tol) {
                return false;
            }
        }
        return true;
    }
}

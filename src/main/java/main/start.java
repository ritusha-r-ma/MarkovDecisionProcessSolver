package main;

import graph.Graph;
import solver.MDPSolver;
import solver.SingleBackwardInduction;
import utilities.CommonUtil;
import validation.ArgValidation;
import validation.FileValidation;
import validation.GraphValidation;

public class start {
    public static void main (String[] args) {
        ArgValidation arg = new ArgValidation();
        arg.readArgument(args);

        FileValidation fileValidation = new FileValidation();
        fileValidation.readInputFile(ArgValidation.getFilePath());
        GraphValidation graphValidation = new GraphValidation();

        // If graph is valid then proceed with execution
        if (graphValidation.isGraphValid(fileValidation.data)) {
            Graph graph = new Graph();
            Graph g = graph.createGraph(fileValidation.data);

            if (CommonUtil.isCyclicGraph(g)) {
                MDPSolver mdp = new MDPSolver();
                mdp.applyMDP(graph.getGraphMap());
            } else {
                SingleBackwardInduction backInduction = new SingleBackwardInduction();
                backInduction.execute(graph.getGraphMap());
            }
        }
    }
}

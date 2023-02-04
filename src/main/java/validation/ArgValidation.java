package validation;

import utilities.ErrorHandlingUtil;

/**
 * Argument handling and validating class
 */
public class ArgValidation {
    static double discountFactor;
    static boolean isMin;
    static double tolerance;
    static int iteration;
    static String filePath;

    public ArgValidation() {
        discountFactor = 1;
        isMin = false;
        tolerance = 0.01;
        iteration = 100;
        filePath = "";
    }

    public void readArgument(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-df")) {
                discountFactor = Double.parseDouble(args[++i]);
                if (discountFactor > 1) {
                    ErrorHandlingUtil.errorOccurred("Discount Factor can't greater than one");
                }
            } else if (args[i].equals("-min")) {
                isMin = true;
            } else if (args[i].equals("-tol")) {
                tolerance = Double.parseDouble(args[++i]);
            } else if (args[i].equals("-iter")) {
                iteration = Integer.parseInt(args[++i]);
            } else if (args[i].contains(".txt")) {
                filePath = args[i];
            }
        }
    }

    public static double getDiscountFactor() {
        return discountFactor;
    }

    public static boolean getIsMin() {
        return isMin;
    }

    public static double getTolerance() {
        return tolerance;
    }

    public static int getIteration() {
        return iteration;
    }

    public static String getFilePath() {
        return filePath;
    }
}

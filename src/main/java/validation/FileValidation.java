package validation;

import utilities.ErrorHandlingUtil;
import graph.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This file parse inputFile.txt and store the values of graph
 */
public class FileValidation {

    public Data data;

    public FileValidation() {
        data = new Data();
    }

    public void readInputFile(String ioPath) {
        int lineNumber = 1;
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(ioPath));
            while ((line = br.readLine()) != null) {
                massageInputFile(line, lineNumber);
                lineNumber++;
            }
        } catch (Exception e) {
            ErrorHandlingUtil.errorOccurred("Error on " + lineNumber + " : " + e.getMessage());
        }
    }

    /**
     * Read the file and process into graph
     * @param line
     * @param lineNumber
     */
    private void massageInputFile(String line, int lineNumber) {
        if (line.contains("#") || line.isEmpty()) {
            return;
        }

        if (line.contains(":")) {
            String[] text = line.split(":");

            if (text.length != 2 || !text[1].contains("[") || !text[1].contains("]")) {
                ErrorHandlingUtil.errorOccurred(lineNumber + "of the text file contains single parameter only");
            }

            String[] neighbour = text[1].replace("[", "").replace("]", "").split(",");
            List<String> neighbors = (neighbour.length == 1 && neighbour[0].isEmpty()) ? new ArrayList<>() : Arrays.stream(neighbour).map(String::trim).collect(Collectors.toList());

            this.data.addAllNodes(text[0].trim(), neighbors);
        } else if (line.contains("=")) {
            String[] text = line.split("=");

            if (text.length != 2) {
                ErrorHandlingUtil.errorOccurred(lineNumber + "of the text file contains more than single reward for single node");
            }

            this.data.addReward(text[0].trim(), Integer.parseInt(text[1]));
        } else if (line.contains("%")) {
            String[] text = line.split("%");

            if (text.length < 2) {
                ErrorHandlingUtil.errorOccurred(lineNumber + "of the text file contains bad parameter");
            }

            String[] probData = text[1].trim().split(" ");
            List<Double> probList = ((probData.length == 1) && probData[0].isEmpty()) ? new ArrayList<>() :
                    Arrays.stream(probData).map(Double::parseDouble).collect(Collectors.toList());

            this.data.addProbability(text[0].trim(), probList);
        } else {
            ErrorHandlingUtil.errorOccurred(lineNumber + "of the text file contains bad parameter");
        }
    }
}

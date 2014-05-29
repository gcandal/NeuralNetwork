package neuralNetwork;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static ArrayList<ArrayList<Double>> test, train;
    private static double error = 0.0000001;

    @SuppressWarnings("unused")
    private static void exportToCSV(int numberOfTrainingRows, ArrayList<ArrayList<Double>> inputs,
                                    String trainFilename, String testFilename) {
        try {
            BufferedWriter file = new BufferedWriter(new FileWriter(trainFilename));

            int i = 0;
            boolean writtingTest = false;

            for (ArrayList<Double> line : inputs) {
                for (int b = 0; b < line.size() - 2; b++)
                    file.write(line.get(b) + ",");
                /*
                    if(b == 0)
						file.write(line.get(b)+",");
					else if(line.get(b) == 1)
						file.write("1,-1,");
					else
						file.write("-1,1,");
				 */

				/*
                if(line.get(line.size()-2) == -1)
					file.write("1,-1,");
				else file.write("-1,1,");

				if(line.get(line.size()-1) == -1)
					file.write("1,-1,");
				else file.write("-1,1,");
				 */

				/*
                if(line.get(line.size()-1) == 1) {
					if(line.get(line.size()-2) == 1)
						file.write("1");
					else
						file.write("-0.5");
				} else if(line.get(line.size()-2) == 1)
					file.write("0.5");
				else file.write("-1");
				 */

                if (line.get(line.size() - 1) == 1) {
                    if (line.get(line.size() - 2) == 1) file.write("1,0,0,0");
                    else file.write("0,1,0,0");
                } else if (line.get(line.size() - 2) == 1) file.write("0,0,1,0");
                else file.write("0,0,0,1");

                file.write('\n');

                if (!writtingTest && ++i >= numberOfTrainingRows && !testFilename.equals("")) {
                    file.close();

                    file = new BufferedWriter(new FileWriter(testFilename));
                    writtingTest = true;
                }
            }

            file.close();
        } catch (IOException e) {
            System.out.println("There was a problem writting the .csv file");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private static void mergeOutputs(String infile, String outfile, boolean justOne) throws IOException {
        BufferedReader iFile = new BufferedReader(new FileReader(infile));
        BufferedWriter oFile = new BufferedWriter(new FileWriter(outfile));
        String line;
        String[] split;

        while (true) {
            line = iFile.readLine();

            if (line == null || line.isEmpty()) {
                iFile.close();
                oFile.close();
                return;
            }

            split = line.split("\t");

            for (int i = 0; i < split.length - 2; i++)
                if (i == 0) oFile.write(split[i].replace(',', '.') + ",");
                else oFile.write(split[i] + ",");

            if (!justOne) {
                if (split[split.length - 2].equals("yes")) {
                    if (split[split.length - 1].equals("yes")) oFile.write("ambas");
                    else oFile.write("primeira");
                } else if (split[split.length - 1].equals("yes")) oFile.write("segunda");
                else oFile.write("nenhuma");
            } else if (split[split.length - 2].equals("yes")) oFile.write("yes");
            else oFile.write("no");


            oFile.write('\n');
        }
    }

    public static void encodeForNeuralNetwork(ArrayList<ArrayList<Double>> inputs, int numberOfTrainingRows) {
        test = new ArrayList<ArrayList<Double>>(inputs.size() - numberOfTrainingRows);
        train = new ArrayList<ArrayList<Double>>(numberOfTrainingRows);

        ArrayList<Double> currentLine = new ArrayList<Double>(inputs.get(0).size());
        boolean writtingTest = false;
        int i = 0;

        for (ArrayList<Double> line : inputs) {
            for (int b = 0; b < line.size() - 2; b++)
                currentLine.add(line.get(b));

            if (line.get(line.size() - 1) == 1) {
                if (line.get(line.size() - 2) == 1) {
                    currentLine.add(1.0);
                    currentLine.add(0.0);
                    currentLine.add(0.0);
                    currentLine.add(0.0);
                } else {
                    currentLine.add(0.0);
                    currentLine.add(1.0);
                    currentLine.add(0.0);
                    currentLine.add(0.0);
                }
            } else if (line.get(line.size() - 2) == 1) {
                currentLine.add(0.0);
                currentLine.add(0.0);
                currentLine.add(1.0);
                currentLine.add(0.0);
            } else {
                currentLine.add(0.0);
                currentLine.add(0.0);
                currentLine.add(0.0);
                currentLine.add(1.0);
            }

            if (writtingTest) test.add(currentLine);
            else train.add(currentLine);

            if (++i >= numberOfTrainingRows) writtingTest = true;

            currentLine = new ArrayList<Double>(inputs.get(0).size());
        }

    }

    @SuppressWarnings({"unused", "serial"})
    public static void main(String[] args) {
        Parser parser;
        NeuralNetwork network;
        int nOutputs = 4;

        try {
            parser = new Parser("diagnosis.txt", 35, 42);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't open file");
            return;
        }

        ArrayList<ArrayList<Double>> inputs;

        try {
            inputs = parser.parseFile();
        } catch (IOException e) {
            System.out.println("There was a problem reading the file");
            return;
        }

        Collections.shuffle(inputs);
        encodeForNeuralNetwork(inputs, 80);

        /*
        exportToCSV(80, inputs, "train.txt", "test.txt");
        try {
            mergeOutputs("diagnosis.txt", "merged.txt", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        network = new NeuralNetwork(new ArrayList<Integer>() {
            {
                //layers and number of perceptrons in each layer
                add(6);
                add(5);
                add(4);
            }
        }, 0.7 /*learning rate*/);

        int i = 1;
        double networkError = 0.0;

        System.out.println("FC: "+network.getError());
        do{
            System.out.println("Iteracao: " + i);
            i++;
            for (ArrayList<Double> inputLine : train.subList(0, 80)) {
                network.feedForward(new ArrayList<Double>(inputLine.subList(0, 6)));
                network.backPropagate(new ArrayList<Double>(inputLine.subList(6, inputLine.size())));
            }

            networkError = network.getError();
            System.out.println("FC: " + networkError);
        } while (networkError>0.01);

        network.testNetwork(test);
        networkError = network.getError();
        System.out.println("Test FC: " + networkError);
    }
}

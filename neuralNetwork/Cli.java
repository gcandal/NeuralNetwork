package neuralNetwork;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Cli {
    private String input;
    private final Scanner in;

    public Cli() {
        in = new Scanner(System.in);
    }

    public String askForDataset() {
        input = "";
        while (!fileExists(input)) {
            System.out.print("Insert path to file: ");

            input = in.nextLine();
            if (fileExists(input)) {
                System.out.println("File Exists!");
            } else {
                System.out.println("Invalid file path. Please try again with a valid file path");
            }
        }
        return input;
    }

    public double askForLearningRate() {
        double learningRate = 0;
        do {
            System.out.println("Insert the learning rate (0 - 1.0): ");
            try {
                learningRate = in.nextDouble();
            } catch(InputMismatchException e) {
                System.out.println("Please insert a number");
                in.nextLine();
            }
            if (learningRate > 1.0 || learningRate <= 0.0) {
                System.out.println("Learning rate must be a double between 0 and 1!");
            }
        } while (learningRate > 1.0 || learningRate <= 0.0); return learningRate;
    }
    
    public double askForMomentum() {
        double momentum = 0;
        do {
            System.out.println("Insert the momentum rate (0 - 1.0): ");
            try {
            	momentum = in.nextDouble();
            } catch(InputMismatchException e) {
                System.out.println("Please insert a number");
                in.nextLine();
            }
            if (momentum > 1.0 || momentum < 0.0) {
                System.out.println("Momentum must be a double between 0 and 1!");
            }
        } while (momentum > 1.0 || momentum < 0.0); return momentum;
    }

    public ArrayList<Integer> askForLayers() {
        int nLayers = 0;
        int temp = 0;
        ArrayList<Integer> result = new ArrayList<>();
        do {
            System.out.println("Insert the number of layers on the network (including input and output): ");
            try {
                nLayers = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please insert a number");
                in.nextLine();
            }
            if (nLayers < 2) {
                System.out.println("Please choose at least 2 layers!");
            }
        } while (nLayers < 2);

        for (int i = 0; i < nLayers; i++) {
            System.out.println("Insert the number of neurons for layer number " + (i + 1) + ": ");
            try {
                temp = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please insert a number");
                in.nextLine();
            }
            if (temp < 1) {
                System.out.println("Please choose at least 1 neuron!");
                i--;
            } else {
                result.add(temp);
            }
        }

        return result;
    }


    private static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public double askForDesiredError() {
        double desiredError = 0;
        do {
            System.out.println("Insert the desired maximum error: ");
            try {
                desiredError = in.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Please insert a number");
                in.nextLine();
            }
            if (desiredError <= 0.0) {
                System.out.println("Maximum error must be > 0");
            }
        } while (desiredError > 1.0 || desiredError <= 0.0);
        return desiredError;
    }

    private String getUserInputForTest() {
        String result = "";
        for (int i = 0; i < Main.getNetwork().getLayers().get(0).size(); i++) {
            System.out.println("Insert parameter " + (i + 1) + " of " + Main.getNetwork().getLayers().get(0).size() +
                    ": ");
            result += in.nextLine().trim() + "\t";
        }
        result += "yes" + "\t";
        result += "yes" + "\t";
        return result;
    }


    public void whatNow() {
        input = "";
        input = in.nextLine();
        do {
            System.out.println();
            System.out.println();
            System.out.println("What now?");
            System.out.println("1. Show Connections");
            System.out.println("2. Insert data manually for question");
            System.out.println("3. Exit");
            System.out.println();
            System.out.println("Option: ");
            input = in.nextLine();
            switch (input) {
                case "Show":
                case "SHOW":
                case "show":
                case "1":
                    System.out.println(Main.getNetwork().toString());
                    break;
                case "Insert":
                case "INSERT":
                case "insert":
                case "2":
                    switch (Main.getNetwork().processUserInput(getUserInputForTest())) {
                        // (nenhuma, inﬂamação da bexiga, nefritite aguda e ambas) d
                        case 0:
                            System.out.println("Diagnosis: Bladder Infection & Acute Nefrititis");
                            break;
                        case 1:
                            System.out.println("Diagnosis: Acute Nephritis");
                            break;
                        case 2:
                            System.out.println("Diagnosis: Bladder Infection");
                            break;
                        case 3:
                            System.out.println("Diagnosis: Nothing Wrong");
                            break;
                        default:
                            System.out.println("Error!");
                    }
                    break;
                case "Exit":
                case "EXIT":
                case "exit":
                case "3":
                    return;
                default:
                    System.out.println("Invalid Input!");
                    System.out.println();
                    return;
            }
        } while (true);
    }

}

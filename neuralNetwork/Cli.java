package neuralNetwork;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Vinnie on 29-May-14.
 */
public class Cli {
    private String input;
    private final Scanner in;

    public Cli() {
        in = new Scanner(System.in);
    }

    public String askForDataset() {
        input = "";
        while (fileExists(input) == false) {
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
        input = "";
        double learningRate;
        do {
            System.out.println("Insert the learning rate (0 - 1.0): ");
            learningRate = in.nextDouble();
            if (learningRate > 1.0 || learningRate <= 0.0) {
                System.out.println("Learning rate must be a double between 0 and 1!");
            }
        } while (learningRate > 1.0 || learningRate <= 0.0);
        return learningRate;
    }

    public ArrayList<Integer> askForLayers() {
        input = "";
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


    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}

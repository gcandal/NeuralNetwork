package neuralNetwork;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	@SuppressWarnings({ "unused", "serial" })
	public static void main(String[] args) {
		Parser parser = null;
		int nOutputs = 2;
		
		try {
			parser = new Parser("diagnosis.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't open file");

			return;
		}
		ArrayList<ArrayList<Double>> inputs = null;

		try {
			inputs = parser.parseFile();
		} catch (IOException e) {
			System.out.println("There was a problem reading the file");

			return;
		}

		//for (ArrayList<Double> inputSet : inputs)
			//System.out.println(inputSet);

		System.out.println(new NeuralNetwork(new ArrayList<Integer>() {{ add(1); add(2); add(2); add(3); }}));
	}
}

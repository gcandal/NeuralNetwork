package neuralNetwork;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	@SuppressWarnings({ "unused", "serial" })
	public static void main(String[] args) {
		Parser parser = null;
		NeuralNetwork network;
		int nOutputs = 2;

		try {
			parser = new Parser("diagnosis.txt", 34, 43);
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
		
		//System.out.println(inputs);
		
		network = new NeuralNetwork(new ArrayList<Integer>() {
			{
				add(1);
				add(2);
				add(1);
			}
		}, 0.25);
		network.feedForward(new ArrayList<Double>() {
			{
				add(1.0);
			}
		});
		System.out.println(network.getOutput());
	}
}

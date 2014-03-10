package neuralNetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	private BufferedReader file;

	public Parser(String filename) throws FileNotFoundException {
		file = new BufferedReader(new FileReader(filename));
	}

	public ArrayList<ArrayList<Double>> parseFile() throws IOException {
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		String line = "";

		while (true) {
			line = file.readLine();

			if (line == null || line.isEmpty())
				return result;

			result.add(parseLine(line));
		}
	}

	private ArrayList<Double> parseLine(String line) {
		ArrayList<Double> result = new ArrayList<Double>();

		for (String word : line.split("\t"))
			try {
				result.add(Double.parseDouble(word.replace(",", ".")));
			} catch (NumberFormatException e) {
				if (word.equals("yes"))
					result.add(1.0);
				else
					result.add(0.0);
			}

		return result;
	}
}

package neuralNetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;

class Parser {
	private BufferedReader file;
	private int lowerBound = 0, upperBound = 0;

    public Parser(String filename) throws FileNotFoundException {
        file = new BufferedReader(new FileReader(filename));
    }

    public Parser(int newLowerBound, int newUpperBound) {
        if(newUpperBound < newLowerBound)
            throw new RuntimeErrorException(null, "Lower bound must be less than the upper bound");

        lowerBound = newLowerBound;
        upperBound = newUpperBound;
    }
	
	public Parser(String filename, int newLowerBound, int newUpperBound) throws FileNotFoundException {
		file = new BufferedReader(new FileReader(filename));
		
		if(newUpperBound < newLowerBound)
			throw new RuntimeErrorException(null, "Lower bound must be less than the upper bound");
			
		lowerBound = newLowerBound;
		upperBound = newUpperBound;
	}

	public ArrayList<ArrayList<Double>> parseFile() throws IOException {
		ArrayList<ArrayList<Double>> result = new ArrayList<>();
		String line;

		while (true) {
			line = file.readLine();

			if (line == null || line.isEmpty())
				return result;

			result.add(parseLine(line));
		}
	}

	public ArrayList<Double> parseLine(String line) {
		ArrayList<Double> result = new ArrayList<>();

		for (String word : line.split("\t"))
			try {
				Double num = Double.parseDouble(word.replace(",", "."));
				
				if(lowerBound == 0 && upperBound == 0)
					result.add(num);
				else
					result.add(normalizeNumber(num));
			} catch (NumberFormatException e) {
				if (word.equals("yes"))
					result.add(1.0);
				else
					result.add(-1.0);
			}

		return result;
	}
	
	private Double normalizeNumber(Double number) {
		return ( number - lowerBound ) / ( ( upperBound - lowerBound ) * 0.5 ) - 1;
	}
}

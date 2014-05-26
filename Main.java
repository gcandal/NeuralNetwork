package neuralNetwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

	private static ArrayList< ArrayList<Double> > test, train;

	@SuppressWarnings("unused")
	private static void exportToCSV(int numberOfTrainingRows, ArrayList<ArrayList<Double>> inputs, String trainFilename, String testFilename) {
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(trainFilename));

			int i = 0;
			boolean writtingTest = false;

			for(ArrayList<Double> line: inputs) {
				for(int b = 0; b < line.size() - 2; b++)
					file.write(line.get(b)+",");
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

				if(line.get(line.size()-1) == 1) {
					if(line.get(line.size()-2) == 1)
						file.write("1,0,0,0");
					else
						file.write("0,1,0,0");
				} else if(line.get(line.size()-2) == 1)
					file.write("0,0,1,0");
				else file.write("0,0,0,1");

				file.write('\n');

				if(!writtingTest && ++i >= numberOfTrainingRows && !testFilename.equals("")) {
					file.close();

					file = new BufferedWriter(new FileWriter(testFilename));
					writtingTest = true;
				}
			}

			file.close();
		} catch(IOException e) {
			System.out.println("There was a problem writting the .csv file");
			e.printStackTrace();

			return;
		}
	}

	@SuppressWarnings("unused")
	private static void mergeOutputs(String infile, String outfile, boolean justOne) throws IOException {
		BufferedReader ifile = new BufferedReader(new FileReader(infile));
		BufferedWriter ofile = new BufferedWriter(new FileWriter(outfile));
		String line = "";
		String[] split;

		while (true) {
			line = ifile.readLine();

			if (line == null || line.isEmpty()) {
				ifile.close();
				ofile.close();
				return;
			}

			split = line.split("\t");

			for(int i = 0; i < split.length-2; i++)
				if(i==0)
					ofile.write(split[i].replace(',', '.')+",");
				else
					ofile.write(split[i]+",");

			if(!justOne) {
				if(split[split.length-2].equals("yes")) {
					if(split[split.length-1].equals("yes"))
						ofile.write("ambas");
					else
						ofile.write("primeira");
				} else if(split[split.length-1].equals("yes"))
					ofile.write("segunda");
				else ofile.write("nenhuma");
			} else if(split[split.length-2].equals("yes"))
				ofile.write("yes");
			else ofile.write("no");


			ofile.write('\n');
		}
	}

	public static void encodeForNeuralNetowrk(ArrayList< ArrayList<Double> > inputs, int numberOfTrainingRows) {
		test = new ArrayList< ArrayList<Double> > (inputs.size() - numberOfTrainingRows);
		train = new ArrayList< ArrayList<Double> > (numberOfTrainingRows);
		
		ArrayList<Double> currentLine = new ArrayList<Double>(inputs.get(0).size());
		boolean writtingTest = false;
		int i = 0;

		for(ArrayList<Double> line: inputs) {
			for(int b = 0; b < line.size() - 2; b++)
				currentLine.add(line.get(b));

			if(line.get(line.size()-1) == 1) {
				if(line.get(line.size()-2) == 1) {
					currentLine.add(1.0);
					currentLine.add(0.0);
					currentLine.add(0.0);
					currentLine.add(0.0);
				}
				else {
					currentLine.add(0.0);
					currentLine.add(1.0);
					currentLine.add(0.0);
					currentLine.add(0.0);
				}
			} else if(line.get(line.size()-2) == 1) {
				currentLine.add(0.0);
				currentLine.add(0.0);
				currentLine.add(1.0);
				currentLine.add(0.0);
			}
			else {
				currentLine.add(0.0);
				currentLine.add(0.0);
				currentLine.add(0.0);
				currentLine.add(1.0);
			}

			if(writtingTest)
				test.add(currentLine);
			else
				train.add(currentLine);

			if(++i >= numberOfTrainingRows) 
				writtingTest = true;

			currentLine = new ArrayList<Double>(inputs.get(0).size());
		}

	}

	@SuppressWarnings({ "unused", "serial" })
	public static void main(String[] args) {
		Parser parser = null;
		NeuralNetwork network;
		int nOutputs = 2;

		try {
			parser = new Parser("diagnosis.txt", 35, 42);
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

		Collections.shuffle(inputs);
		encodeForNeuralNetowrk(inputs, 80);

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

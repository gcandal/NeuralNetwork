package neuralNetwork;

import java.util.Random;

public class Synapse {
	private static final Random random = new Random();
	private static int count = 0, randomWeightLimit = 1;
	private final int id;
	private double weight = 0;
	private final Neuron from, to;
	
	public Synapse(Neuron in, Neuron out) {
		from = in;
		to = out;
		id = count++;
		
		resetWeight();
	}
	
	public void resetWeight() {
		weight = random.nextDouble() * 2 * randomWeightLimit - randomWeightLimit;
	}
	
	public Neuron getFromNeuron() {
		return from;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double newWeight) {
		weight = newWeight;
	}
	
	public String toString() {
		return from.getId() + " == " + weight + " >> " + to.getId(); 
	}
	
	public Integer getId() {
		return id;
	}
}

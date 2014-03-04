package neuralNetwork;

import java.util.HashMap;

public class Neuron {
	private static int count = 0;
	private final int id;
	
	private HashMap<Integer,Synapse> incomingSynapses = new HashMap<Integer,Synapse>();
	
	public Neuron() {
		id = count++;
	}
	
	public void addIncomingSynapse(Synapse synapse) {
		incomingSynapses.put(synapse.getId(), synapse);
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		for(Synapse synapse: incomingSynapses.values())
			result.append(synapse).append("\n");
		
		return result.toString();
	}
}

package neuralNetwork;

import java.util.HashMap;

public class Neuron {
	private static int count = 0;
	private double output = 0, error = 0;
	private final int id;

	private HashMap<Integer, Synapse> incomingSynapses = new HashMap<Integer, Synapse>();
    private HashMap<Integer, Synapse> outgoingSynapses = new HashMap<Integer, Synapse>();

    public Neuron() {
        id = count++;
    }

	public void addIncomingSynapse(Synapse synapse) {
		incomingSynapses.put(synapse.getId(), synapse);
	}

    public void addOutgoingSynapse(Synapse synapse) {
        outgoingSynapses.put(synapse.getId(), synapse);
    }

	public int getId() {
		return id;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		for (Synapse synapse : incomingSynapses.values())
			result.append(synapse).append(" IN:" + incomingSynapses.size() + "|OUT: " + outgoingSynapses.size() + "\n");

		return result.toString();
	}

	public void calculateOutput() {
		double sum = 0;
		for (Synapse synapse : incomingSynapses.values()) {
			sum += synapse.getWeight() * synapse.getFromNeuron().getOutput();
		}
		output = sigmoid(sum);
	}

	private double sigmoid(double sum) {
		return 1 / (1 + Math.pow(Math.E, -sum));
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(Double newOutput) {
		output = newOutput;
	}

    public double getError() {
        return error;
    }

	public void setError(double expected) {
		error = output * (1 - output) * (expected - output);
	}

	public void changeWeightSynapses(double learningRate) {
		for (Synapse synapse : incomingSynapses.values()) {
			synapse.changeWeight(learningRate);
		}
	}

	public void calculateError() {
		double sum = 0;
		for (Synapse synapse : outgoingSynapses.values()) {
			sum += synapse.getWeight() * synapse.getToNeuron().getError();
		}
		error = sum * output * (1 - output);
	}
}

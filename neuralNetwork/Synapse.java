package neuralNetwork;

import java.util.Random;

class Synapse {
    private static final Random random = new Random();
    private static int count = 0;
    private final int id;
    private double weight = 0, previousChange = 0;
    private final Neuron from, to;

    public Synapse(Neuron in, Neuron out) {
        from = in;
        to = out;
        id = count++;

        resetWeight();
    }

    void resetWeight() {
        int randomWeightLimit = 1;
        weight = random.nextDouble() * 2 * randomWeightLimit - randomWeightLimit;
    }

    public Neuron getFromNeuron() {
        return from;
    }

    public Neuron getToNeuron() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double newWeight) {
        weight = newWeight;
    }

    public String toString() {
        return from.getId() + "(" + from.getOutput() + ")" + " == " + weight + " >> " + to.getId() + "(" + to.getOutput() + ")";
    }

    public Integer getId() {
        return id;
    }

    public void changeWeight(double learningRate, double momentum) {
    	double newChange = learningRate * to.getError() * from.getOutput();
        weight += newChange + momentum*previousChange;
    	previousChange = newChange;
    }
}

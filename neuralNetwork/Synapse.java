package neuralNetwork;

import java.util.Random;

public class Synapse {
    private static final Random random = new Random();
    private static int count = 0;
    private final int id;
    private double weight = 0;
    double prevDeltaWeight = 0;
    double deltaWeight = 0;
    private final Neuron from, to;

    public Synapse(Neuron in, Neuron out) {
        from = in;
        to = out;
        id = count++;

        resetWeight();
    }


    public void setDeltaWeight(double weight) {
        prevDeltaWeight = deltaWeight;
        deltaWeight = weight;
    }

    public double getPrevDeltaWeight() {
        return prevDeltaWeight;
    }

    public void resetWeight() {
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

    public void changeWeight(double learningRate) {
        //System.out.println(from.getId() + "===>" + to.getId());
        weight += learningRate * to.getError() * from.getOutput();
    }
}

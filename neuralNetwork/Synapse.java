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
        return from.getId() + " == " + weight + " >> " + to.getId();
    }

    public Integer getId() {
        return id;
    }

    public void changeWeight(double error, double learningRate, double momentum) {

        double ak = this.getToNeuron().getOutput();
        double ai = this.getFromNeuron().getOutput();
        //TODO: get expected output and all is well.
            /*double desiredOutput = expectedOutput[i];

                double partialDerivative = -ak * (1 - ak) * ai
                        * (desiredOutput - ak);
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());*/
        weight += learningRate * error * from.getOutput() * to.getOutput() * (1 - to.getOutput());
    }
}

package neuralNetwork;

import java.util.ArrayList;

public class NeuralNetwork {

    private ArrayList<ArrayList<Neuron>> layers;
    private ArrayList<Double> squaredErrors = new ArrayList<Double>();
    private int exampleCount = 0;
    private double learningRate = 0.2;
    private Neuron bias = new Neuron();

    public NeuralNetwork(ArrayList<Integer> layerNumbers, double newLearningRate) {
        learningRate = newLearningRate;
        bias.setOutput(1.0);

        layers = new ArrayList<ArrayList<Neuron>>(layerNumbers.size());

        for (int i = 0; i < layerNumbers.size(); i++) {
            initializeLayer(layerNumbers.get(i), i);
        }
    }

    public void feedForward(ArrayList<Double> input) {
        if (input.size() != layers.get(0).size()) throw new RuntimeException("Invalid Input Size");

        initializeInputLayer(input);
        for (ArrayList<Neuron> layer : layers.subList(1, layers.size())) {
            for (Neuron neuron : layer) {
                neuron.calculateOutput();
            }
        }
    }

    public void backPropagate(ArrayList<Double> output) {
        if (output.size() != layers.get(layers.size() - 1).size()) throw new RuntimeException("Invalid Output Size");

        calculateOutputErrors(output);
        for (int i = layers.size() - 1; i > 0; i--)
            for (Neuron neuron : layers.get(i)) {
                if (i < layers.size() - 1) neuron.calculateError();
                neuron.changeWeightSynapses(learningRate);
            }

        exampleCount++;
        for (int i = 0; i < output.size(); i++)
            squaredErrors.add(layers.get(layers.size() - 1).get(i).getOutput() - output.get(i));
    }

    public double testNetwork(ArrayList<ArrayList<Double>> test) {
        double failedResults = 0.0;
        double correctResults = 0.0;
        for (ArrayList<Double> inputLine : test) {
            if (testingFeedForward(new ArrayList<Double>(inputLine.subList(0, 6)),
                    new ArrayList<Double>(inputLine.subList(6, inputLine.size())))) {
                correctResults++;
            } else {
                failedResults++;
            }
        }

        return Math.abs(correctResults - failedResults) / (correctResults + failedResults);
    }

    public boolean testingFeedForward(ArrayList<Double> input, ArrayList<Double> output) {
        if (input.size() != layers.get(0).size()) throw new RuntimeException("Invalid Input Size");

        initializeInputLayer(input);
        for (ArrayList<Neuron> layer : layers.subList(1, layers.size())) {
            for (Neuron neuron : layer) {
                neuron.calculateOutput();
            }
        }

        int biggestOutputIndex = biggestNumber(output);
        int biggestNetworkOutputIndex = getBiggestOutputFromLayer(layers.get(layers.size() - 1));

        exampleCount++;
        for (int i = 0; i < output.size(); i++)
            squaredErrors.add(layers.get(layers.size() - 1).get(i).getOutput() - output.get(i));

        return biggestOutputIndex == biggestNetworkOutputIndex;
    }

    private void initializeLayer(int layerSize, int index) {
        ArrayList<Neuron> layer = new ArrayList<Neuron>(layerSize);

        for (int i = 0; i < layerSize; i++) {
            layer.add(new Neuron());
        }

        layers.add(index, layer);
        if (index != 0) connectLayers(index);

    }

    private void connectLayers(int index) {
        for (Neuron fromNeuron : layers.get(index - 1))
            for (Neuron toNeuron : layers.get(index)) {
                toNeuron.addIncomingSynapse(new Synapse(fromNeuron, toNeuron));
                fromNeuron.addOutgoingSynapse(new Synapse(fromNeuron, toNeuron));
            }

        for (Neuron toNeuron : layers.get(index))
            toNeuron.addIncomingSynapse(new Synapse(bias, toNeuron));
    }


    public String toString() {
        return "Input Neuron ==>> Hidden Neuron\n" + hiddenLayersToString() + "Hidden Neuron ==>> Output Neuron\n" +
                layerToString(layers.get(layers.size() - 1));
    }

    private String hiddenLayersToString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < layers.size() - 1; i++) {
            result.append("Layer " + i + "\n");
            result.append(layerToString(layers.get(i))).append("\n");
        }

        return result.toString();
    }

    private String layerToString(ArrayList<Neuron> layer) {
        StringBuilder result = new StringBuilder();

        for (Neuron neuron : layer)
            result.append(neuron);

        return result.toString();
    }

    private void initializeInputLayer(ArrayList<Double> input) {
        for (int i = 0; i < layers.get(0).size(); i++) {
            layers.get(0).get(i).setOutput(input.get(i));
        }
    }

    private void calculateOutputErrors(ArrayList<Double> expectedOutput) {
        for (int i = 0; i < layers.get(layers.size() - 1).size(); i++) {
            layers.get(layers.size() - 1).get(i).setError(expectedOutput.get(i));
        }
    }

    public ArrayList<Double> getOutput() {
        ArrayList<Double> result = new ArrayList<Double>();

        for (Neuron neuron : layers.get(layers.size() - 1))
            result.add(neuron.getOutput());

        return result;
    }

    public double getError() {
        double sum = 0;
        for (int i = 0; i < squaredErrors.size(); i++)
            sum += squaredErrors.get(i);

        sum /= 2 * exampleCount;
        exampleCount = 0;
        squaredErrors.clear();

        return sum;
    }

    private int getBiggestOutputFromLayer(ArrayList<Neuron> neurons) {
        int biggest = 0;
        for (int i = 1; i < neurons.size(); i++)
            if (neurons.get(i).getOutput() > neurons.get(biggest).getOutput()) biggest = i;
        return biggest;
    }

    private int biggestNumber(ArrayList<Double> output) {
        int biggest = 0;
        for (int i = 1; i < output.size(); i++)
            if (output.get(i) > output.get(biggest)) biggest = i;
        return biggest;
    }
}


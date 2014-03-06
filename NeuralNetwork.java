package neuralNetwork;

import java.util.ArrayList;

public class NeuralNetwork {
	
	private ArrayList<ArrayList<Neuron>> layers;

	public NeuralNetwork(ArrayList<Integer> layerNumbers) {
		layers = new ArrayList<ArrayList<Neuron>>(layerNumbers.size());
		
		for (int i = 0; i < layerNumbers.size(); i++) {
			initializeLayer(layerNumbers.get(i), i);
		}
	}

	private void initializeLayer(int layerSize, int index) {
		ArrayList<Neuron> layer = new ArrayList<Neuron>(layerSize);

		for (int i = 0; i < layerSize; i++)
			layer.add(new Neuron());
		
		layers.add(index, layer);
		if(index != 0) 
			connectLayers(index);
			
	}

	private void connectLayers(int index) {
		for (Neuron fromNeuron : layers.get(index - 1))
			for (Neuron toNeuron : layers.get(index))
				toNeuron.addIncomingSynapse(new Synapse(fromNeuron, toNeuron));
	}

	public String toString() {
		return "Input Neuron ==>> Hidden Neuron"
				+ hiddenLayerstoString()
				+ "Hidden Neuron ==>> Output Neuron\n"
				+ layerToString(layers.get(layers.size()-1));
	}
	
	private String hiddenLayerstoString() {
		StringBuilder result = new StringBuilder();
		
		for(ArrayList<Neuron> layer: layers.subList(0, layers.size() - 1))
			result.append(layerToString(layer)).append("\n");
		
		return result.toString();
	}

	private String layerToString(ArrayList<Neuron> layer) {
		StringBuilder result = new StringBuilder();

		for (Neuron neuron : layer)
			result.append(neuron);

		return result.toString();
	}

	public void feedForward(ArrayList<Double> input) {
		if(input.size() != layers.get(0).size()) 
			throw new RuntimeException("Invalid Input Size");
		
		initializeInputLayer(input);
		for(ArrayList<Neuron> layer: layers.subList(1, layers.size())){
			for(Neuron neuron: layer){
				neuron.calculateOutput();
			}
		}
	}

	private void initializeInputLayer(ArrayList<Double> input) {
		for(int i = 0; i < layers.get(0).size(); i++) {
			layers.get(0).get(i).setOutput(input.get(i)); 
		}
	}
}

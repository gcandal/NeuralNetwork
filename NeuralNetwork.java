package neuralNetwork;

import java.util.ArrayList;

public class NeuralNetwork {
	private ArrayList<Neuron> inputLayer, hiddenLayer, outputLayer;
	
	//TODO quando estiver tudo feito -> multipla camada hidden
	public NeuralNetwork(int nrInputNeurons, int nrHiddenNeurons, int nrOutputNeurons) {
		inputLayer = initializeLayer(nrInputNeurons);
		hiddenLayer = initializeLayer(nrHiddenNeurons);
		outputLayer = initializeLayer(nrOutputNeurons);
		
		connectLayers(inputLayer, hiddenLayer);
		connectLayers(hiddenLayer, outputLayer);
	}
	
	private ArrayList<Neuron> initializeLayer(int layerSize) {
		ArrayList<Neuron> layer = new ArrayList<Neuron>(layerSize);
		
		for(int i = 0; i < layerSize; i++)
			layer.add(new Neuron());
		
		return layer;
	}
	
	private void connectLayers(ArrayList<Neuron> fromLayer, ArrayList<Neuron> toLayer) {
		for(Neuron fromNeuron: fromLayer)
			for(Neuron toNeuron: toLayer)
				toNeuron.addIncomingSynapse(new Synapse(fromNeuron, toNeuron));
	}
	
	public String toString() {
		return "Input Neuron ==>> Hidden Neuron \n" + layerToString(hiddenLayer) +
				"\nHidden Neuron ==>> Output Neuron \n" + layerToString(outputLayer);
	}
	
	private String layerToString(ArrayList<Neuron> layer) {
		StringBuilder result = new StringBuilder();
		
		for(Neuron neuron: layer)
			result.append(neuron);
		
		return result.toString();
	}
}

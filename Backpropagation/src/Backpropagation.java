import java.util.List;

public class Backpropagation {

	private NeuralNetwork neuralNetwork;
	private double learningRate;
	private double momentum;
	private double currentEpoch;

	public Backpropagation() {
		super();

	}

	public Backpropagation(NeuralNetwork neuralNetwork, double learningRate,
			double momentum) {
		this.neuralNetwork = neuralNetwork;
		this.learningRate = learningRate;
		this.momentum = momentum;
	}

	public void backpropagationAlgorithm(DataTraining dataTraining) {

		try {
			double[] outRealExpected = new double[dataTraining.getReal_out()];
			double[] outputsRealCalculated = new double[dataTraining.getReal_out()];
			double[] error;
			


			// 1.- Initialize weights
			for (Layer layer : neuralNetwork.getLayers()) {
				layer.initializeWeights();
			}

			// 2.-Empezamos suponiniendo q el criterio de parada es un numero
			// fijo
			// de iteracciones
			for (int i = 0; i < 10; i++) {
				// 2.1 Para cada ejemplo (x,y) D Calcular la salida de cada
				// unidad propagando valores hacia adelante
				for (int j = 0; j < dataTraining.getTraining_examples(); j++) {
					// inReal = dataTraining.getRealInputs()[j];
					outRealExpected = dataTraining.getRealOutputs()[j];
					// 2.1.1. Calcular cada salida
					outputsRealCalculated = neuralNetwork.getOutputs(
							dataTraining, j);
					// 2.1.2. Calcular el error
					error = new double[dataTraining.getReal_out()];
					calculateError(outputsRealCalculated, outRealExpected);
					updateWeights(learningRate);
				}
			}


		} catch (Exception e) {
			System.out.println("Backpropagation.backpropagationAlgorithm"+ e.toString());
		}

	}

	private void calculateError(double[] outputsCalculated, double[] expectedOutputs) {
		double errorN;

		try {
			List<Layer> layers = neuralNetwork.getLayers();

			for (int i = layers.size() - 1; i > 0; i--) {
				Layer layer = layers.get(i);
				for (int j = 0; j < layer.getNeurons().size(); j++) {
					Neuron n = layer.getNeurons().get(j);
					// Last layer
					if (layer.isOutputLayer()) {
						// ValueRetropropagtion is the value calculated previously = Ai(1-Ai)
						errorN = n.getValueRetropropagtion() * (expectedOutputs[j] - outputsCalculated[j]);
						n.setError(errorN);
					}
					// Next Layers
					else {
						double sum = 0.0;
						errorN = n.getValueRetropropagtion();
						// ValueRetropropagtion is the value calculated previously = Ai(1-Ai)
						List<Neuron> neuronsNextLayer = layer.getNextlayer().getNeurons();
						for (Neuron neuronNextLayer : neuronsNextLayer) {
							List<Synapse> inputSynNextLayer = neuronNextLayer.getInputSynapses();
							for (Synapse s : inputSynNextLayer) {
								if (s.getSourceNeuron() == neuronNextLayer) {
									sum += s.getWeight() * n.getError();
								}
							}
						}
						errorN *= sum;
						n.setError(errorN);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Backpropagation.calculateError"+ e.toString());
		}
	}	
	
	private void updateWeights(double learningRate)
	{
		List<Layer> layers = neuralNetwork.getLayers();
		double weight = 0.0;
		
		 for(int j = layers.size() - 1; j > 0; j--) {
             Layer layer = layers.get(j);

             for(Neuron neuronCurrent : layer.getNeurons()) {
            	List<Synapse> synapses = neuronCurrent.getInputSynapses();
            	for(int i = 0; i < synapses.size(); i++ )
            	{
            		weight = synapses.get(i).getWeight();
            		Neuron neuronPrev = synapses.get(i).getSourceNeuron();
            		weight += learningRate * neuronPrev.getOutput() * neuronCurrent.getError();
            		neuronPrev.setWeight(weight);
            	}
            	
             }
		 }
	}
}

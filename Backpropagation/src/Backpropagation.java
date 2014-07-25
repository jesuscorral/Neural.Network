
public class Backpropagation {

	private NeuralNetwork neuralNetwork;
	private double learningRate;
	private double momentum;
	private double currentEpoch;

	public Backpropagation(NeuralNetwork neuralNetwork, double learningRate,
			double momentum) {
		this.neuralNetwork = neuralNetwork;
		this.learningRate = learningRate;
		this.momentum = momentum;
	}

	public void backpropagationAlgorithm(NeuralNetwork neuralNetwork, DataTraining dataTraining) {
		//dataTraining.getRealInputs();
		double[][] expectedOutputs;
		
		// 1.- Initialize weights
		for (Layer layer : neuralNetwork.getLayers()) {
			layer.initializeWeights();
		}

		
		// 2.-Empezamos suponiniendo q el criterio de parada es un numero fijo
		// de iteracciones
		for (int i = 0; i < 10; i++) {
			//2.1 Para cada ejemplo (x,y) ∈ D  Calcular la salida de cada unidad propagando valores hacia adelante
			for(Layer layer : neuralNetwork.getLayers())
			{
				
			}
			
		}

	}
	
	
}

public class Backpropagation {

	private NeuralNetwork neuralNetwork;
	private double learningRate;
	private double momentum;
	private double currentEpoch;

	public Backpropagation() {
		super();

	}

	public Backpropagation(NeuralNetwork neuralNetwork, double learningRate,double momentum) {
		this.neuralNetwork = neuralNetwork;
		this.learningRate = learningRate;
		this.momentum = momentum;
	}

	public void backpropagationAlgorithm(DataTraining dataTraining) {

		try {
			double[][] expectedOutputs = new double[dataTraining.getTraining_examples()][dataTraining.getReal_out()];
			double[][] outputs = new double[dataTraining.getTraining_examples()][dataTraining.getReal_out()];

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

				outputs = neuralNetwork.getOutputs(dataTraining);

			}
			
			expectedOutputs = dataTraining.getRealOutputs();
			
/*			//System.out.println("Bien2");
			for (int i = 0; i < dataTraining.getTraining_examples(); i++) {
				for (int j = 0; j < 3; j++) {
					System.out.println("out Real: " + outputs[i][j]);
					System.out.println("out Expected: " + expectedOutputs[i][j]);
				}
				System.out.println("Example->" + i);
			}*/

		} catch (Exception e) {
			System.out.println("Backpropagation.backpropagationAlgorithm"
					+ e.toString());
		}

	}

}

/**
 * @author Jesús Corral Pérez
 *
 */

public class Algorithm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Menu menu = new Menu();
		DataTest dataTest = new DataTest();
		DataTraining dataTraining = new DataTraining();
		DataValidation dataValidation = new DataValidation();
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		ActivationFunction activationFunction = new ActivationFunction();

		try {

			// Show menu and choose some option
			menu.show();
			// Load data training from file
			dataTraining.LoadDataTraining(menu.getPathDataSetOne());
			dataTraining.LoadDataTraining(menu.getPathDataSetTwo());
			dataTraining.LoadDataTraining(menu.getPathDataSetThree());
			
			// Load data validation from file
			dataValidation.LoadDataValidation(menu.getPathDataSetOne());
			dataValidation.LoadDataValidation(menu.getPathDataSetTwo());
			dataValidation.LoadDataValidation(menu.getPathDataSetThree());
			
			// Load data test from file
			dataTest.LoadDataTest(menu.getPathDataSetOne());
			dataTest.LoadDataTest(menu.getPathDataSetTwo());
			dataTest.LoadDataTest(menu.getPathDataSetThree());

			// Add neurons to the input layer. We add as many neurons as input
			// training examples
			Layer inputLayer = new Layer();
			for (int i = 0; i < dataTraining.getTraining_examples(); i++) {
				Neuron n = new Neuron(activationFunction);
				inputLayer.AddNeuron(n);
			}

			Layer hiddenLayer = new Layer(inputLayer);
			int numberOfHiddenLayerNeurons = 1;
			for (int i = 0; i < numberOfHiddenLayerNeurons; i++) {
				Neuron neuron = new Neuron(activationFunction);
				hiddenLayer.AddNeuron(neuron);
			}

			Layer outputLayer = new Layer(hiddenLayer);
			for (int i = 0; i < 2; i++) {
				Neuron neuron = new Neuron(activationFunction);
				outputLayer.AddNeuron(neuron);
			}

			neuralNetwork.addLayer(inputLayer);
			neuralNetwork.addLayer(hiddenLayer);
			neuralNetwork.addLayer(outputLayer);
			
			
		//	Backpropagation bk = new Backpropagation(neuralNetwork, learningRate, momentum)
			
			/*Neuron n = new Neuron(new ActivationFunction());
			n = hiddenLayer.getNeurons().get(0);
			Synapse s = new Synapse();
			s = n.getInputSynapses().get(0);
			System.out.println("valor: " +s.getWeight());
*/
			
			
		} catch (Exception e) {
			System.out.println("Algorithm.main" + e.toString());
		}

	}
}

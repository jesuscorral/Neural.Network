/**
 * @author Jes��s Corral P��rez
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
			// HACER UN MENU PARA PODER ELEGIR ENTRE TRAINING, VALIDATION AND TEST, dependiendo de cada una 
			// pondremos unos limites u otros.
			int numNeuronInput;
			if(dataTraining.getBool_in() != 0)
				numNeuronInput = dataTraining.getBool_in();
			else
				numNeuronInput = dataTraining.getReal_in();
			
			Layer inputLayer = new Layer();
			for (int i = 0; i < numNeuronInput; i++) {
				Neuron n = new Neuron(activationFunction);
				inputLayer.AddNeuron(n);
			}

			Layer hiddenLayer = new Layer(inputLayer);
			int numberOfHiddenLayerNeurons = 1;
			for (int i = 0; i < numberOfHiddenLayerNeurons; i++) {
				Neuron neuron = new Neuron(activationFunction);
				hiddenLayer.AddNeuron(neuron);
			}

			int numNeuronOutput;
			if(dataTraining.getReal_out() != 0)
				numNeuronOutput = dataTraining.getReal_out();
			else
				numNeuronOutput = dataTraining.getBool_out();
			Layer outputLayer = new Layer(hiddenLayer);
			for (int i = 0; i < numNeuronOutput; i++) {
				Neuron neuron = new Neuron(activationFunction);
				outputLayer.AddNeuron(neuron);
			}

			neuralNetwork.addLayer(inputLayer);
			neuralNetwork.addLayer(hiddenLayer);
			neuralNetwork.addLayer(outputLayer);
			
			Backpropagation bk = new Backpropagation(neuralNetwork, 0.1, 0.9);
		
			bk.backpropagationAlgorithm(dataTraining);
	
			
		} catch (Exception e) {
			System.out.println("Algorithm.main" + e.toString());
		}

	}
}


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/*
 * @author Jesús Corral Versión
 * @University Universidad de Sevilla
 * @Subject Inteligencia Artificial 2
 * @Proyect Backpropagation Algorithm with momentum and cross validation for Neural Network
 */

public class Algorithm {
	
	public static void main(String[] args) {
		
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		DataTest dataTest = new DataTest();
		DataTraining dataTraining = new DataTraining();
		DataValidation dataValidation = new DataValidation();
		ActivationFunction activationFunction = new ActivationFunction();
		int numberOfHiddenLayer = 0;
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader (isr);
	 
		int totalEpochs = 0, relevantEpochs = 0, numEx = 0;
		double errorTest = 0.0, testSetClasification = 0.0;
	    double meanErrorTraining = 0.0, meanErrorValidation = 0.0, meanOverfit = 0.0;
	    double stddevErrorTraining = 0.0, stddevErrorValidation = 0.0, stddevOverfit = 0.0;
	   
	    String problem = "";
	    double momentum = 0.0;
	    double learningRate = 0.0;
	    double threshold = 0.0;
	    int maxEpoch = 3000;
	    boolean clasificationProblem = false; //Only clasfication problem need to calculate the "Test Set Clasification"
	    int menuOption = -1;
	    
		try {

			
			// Show main menu with the twelve problems and choose some option
			Menu menu = new Menu(menuOption);
			menu.showMainMenu();
			// Show the submenu with three option by each problem.
			menu.showSubMenu();
			
			do{
			System.out.println("Number of layers (>=0) : ");
			numberOfHiddenLayer= Integer.parseInt(br.readLine());
			if(numberOfHiddenLayer < 0)
				System.out.println("Please insert a number of hidden layer greater or equal than 0.\n");
			}while(numberOfHiddenLayer < 0);

			do{
			System.out.println("Number of executions (>=1): ");
			numEx = Integer.parseInt(br.readLine());
			if(numEx < 1)
				System.out.println("Please insert a number of executions greater or equal than 0.\n");
			}while(numEx < 1);

			
			System.out.println("Learning Rate: ");
			learningRate = Double.parseDouble(br.readLine());
			

			System.out.println("Momentum: ");
			momentum= Double.parseDouble(br.readLine());
			

			System.out.println("Threshold: ");
			threshold= Double.parseDouble(br.readLine());
			
			if (menu.getOption() <= 9 || menu.getOption() >= 0)
				clasificationProblem = true;
			else
				clasificationProblem = false;
				
			
			switch(menu.getNumberOptionSubmenu())
			{
			case 1:
				dataTraining.LoadDataTraining(menu.getPathDataSetOne());
				dataValidation.LoadDataValidation(menu.getPathDataSetOne());
				dataTest.LoadDataTest(menu.getPathDataSetOne());
				problem = menu.getPathDataSetOne();
				break;
			case 2:
				dataTraining.LoadDataTraining(menu.getPathDataSetTwo());
				dataValidation.LoadDataValidation(menu.getPathDataSetTwo());
				dataTest.LoadDataTest(menu.getPathDataSetTwo());
				problem = menu.getPathDataSetTwo();
				break;
			case 3: 
				dataTraining.LoadDataTraining(menu.getPathDataSetThree());
				dataValidation.LoadDataValidation(menu.getPathDataSetThree());
				dataTest.LoadDataTest(menu.getPathDataSetThree());
				problem = menu.getPathDataSetThree();
				break;
			}

			//Input layer
			// Add neurons to the input layer. We add as many neurons as input training examples
			int numNeuronInput;
			if(dataTraining.getBool_in() != 0)
				numNeuronInput = dataTraining.getBool_in();
			else
				numNeuronInput = dataTraining.getReal_in();
			
			for (int cEx = 0; cEx < numEx; cEx++) {
				System.out.println("\n===========================================");
				System.out.println("Run Training number: " + (cEx + 1));
				System.out.println("===========================================\n");
				
			//Input Layer
			Layer inputLayer = new Layer();
			Neuron bias = new Neuron(activationFunction);
			bias.setOutput(-1);
			inputLayer.AddNeuron(bias);
			for (int i = 0; i < numNeuronInput; i++) {
				Neuron n = new Neuron(activationFunction);
				inputLayer.AddNeuron(n);
			}
			neuralNetwork.addLayer(inputLayer);

			//Hidden layer
			Layer hiddenLayer = null;
			for(int k = 0; k<numberOfHiddenLayer; k++)
				{
				hiddenLayer = new Layer(inputLayer);
				Neuron bias2 = new Neuron(activationFunction);
				bias2.setOutput(-1);
				hiddenLayer.AddNeuron(bias2);
				int numberOfHiddenLayerNeurons = numNeuronInput;
				for (int i = 0; i < numberOfHiddenLayerNeurons; i++) {
					Neuron neuron = new Neuron(activationFunction);
					hiddenLayer.AddNeuron(neuron);
				}
				neuralNetwork.addLayer(hiddenLayer);
			}

			//Output layer
			int numNeuronOutput;
			Layer outputLayer;
			if(dataTraining.getReal_out() != 0)
				numNeuronOutput = dataTraining.getReal_out();
			else
				numNeuronOutput = dataTraining.getBool_out();
			
			if(hiddenLayer != null){
				 outputLayer = new Layer(hiddenLayer);
			}else{
			outputLayer = new Layer(inputLayer);
			}
			for (int i = 0; i < numNeuronOutput; i++) {
				Neuron neuron = new Neuron(activationFunction);
				outputLayer.AddNeuron(neuron);
			}
			neuralNetwork.addLayer(outputLayer);
			
			//Exec the algorithm
			Backpropagation bk = new Backpropagation(neuralNetwork, learningRate, momentum, dataTraining, 
					dataValidation, dataTest, numEx, meanErrorTraining,stddevErrorTraining, meanErrorValidation, 
					stddevErrorValidation, errorTest, testSetClasification, meanOverfit, stddevOverfit, 
					totalEpochs, relevantEpochs, clasificationProblem);
			bk.exec(maxEpoch, threshold);

			//Show the results in a text file
			 Pattern patternEq = Pattern.compile("/");
			 String[] problemRow = patternEq.split(problem);
			 
			 String sFichero = "Results.txt";
			 FileWriter fichero = new FileWriter(sFichero, true);
			 
				 fichero.write("*************************************************\n");
				 fichero.write("CONFIGURATION:\n");
				 fichero.write(" Problem: " + 	problemRow[2] + "\n");
				 fichero.write(" Number of execution" + (cEx + 1) + "\n");
				 fichero.write(" Number maximium of epochs: " +maxEpoch);
				 fichero.write("\n Momentum: " +momentum);
				 fichero.write("\n Learning Rate: " +learningRate);
				 fichero.write("\n Threshold: " +threshold);
				 fichero.write("\n Number of hiddens layers " + numberOfHiddenLayer);
				 fichero.write("\n*************************************************\n\n");
				 fichero.write("RESULTS:\n ");
				 fichero.write("\t Training Set \n");
				 fichero.write("\t\t Mean: " + Math.rint(bk.getMeanErrorTraining() * 10000) / 10000 + "\n");
				 fichero.write("\t\t Stddev: " + Math.rint(bk.getStddevErrorTraining() * 10000) / 10000 + "\n");
				 fichero.write("\t Validation Set \n");
				 fichero.write("\t\t Mean: " + Math.rint(bk.getMeanErrorValidation() * 10000) / 10000  + "\n");
				 fichero.write("\t\t Stddev: " + Math.rint(bk.getStddevErrorValidation() * 10000) / 10000  + "\n");
				 fichero.write("\t Test Set \n");
				 fichero.write("\t\t" + Math.rint(bk.getErrorTest() * 10000) / 10000  + "\n");
				 fichero.write("\t Test Set Clasification\n");
				 fichero.write("\t\t" + bk.getTestSetClasification() + "\n");
				 fichero.write("\t Overfit \n");
				 fichero.write("\t\t Mean: " + Math.rint(bk.getMeanOverfit() * 10000) / 10000  + "\n");
				 fichero.write("\t\t Stddev: " + Math.rint(bk.getStddevOverfit() * 10000) / 10000  + "\n");
				 fichero.write("\t Total epochs \n");
				 fichero.write("\t\t" +(int) bk.getTotalEpochs() + "\n");
				 fichero.write("\t Relevant epochs \n");
				 fichero.write("\t\t" + (int)bk.getRelevantEpochs()+ "\n");
				 fichero.close();
				 System.out.println("\n\n File created with success in \""+ sFichero +"\"");
			}//fin for;
		} catch (Exception e) {
			System.out.println("Algorithm.main" + e.toString());
		}

	}


}

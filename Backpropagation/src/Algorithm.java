import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

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
		int numberOfHiddenLayer = 0;
		int numberOfProblem = 0;
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader (isr);
	    int numEx = 0;
		double errorTraining = 0.0, errorValidation = 0.0, errorTest = 0.0, testSetClasification = 0.0, overfit = 0.0, totalEpochs = 0.0, relevantEpochs = 0.0;
	    double[] errorsTraining,errorsValidation, errorsTest, valuesOverfit, numEpochs, numRelevantEpochs;
	    double meanErrorTraining = 0.0, meanErrorValidation = 0.0, meanErrorTest = 0.0, meanOverfit = 0.0, meanEpochs = 0.0, meanRelevantEpochs = 0.0;
	    double stddevTraining = 0.0, stddevTest = 0.0, stddevValidation = 0.0, stddevOverfit = 0.0, stddevEpochs = 0.0, stddevRelevantEpochs = 0.0;
	    String problem = "";
	    
		try {
			System.out.println("How many hidden layers do you want?");
			numberOfHiddenLayer= Integer.parseInt(br.readLine());
			
			// Show menu and choose some option
			menu.show();
			System.out.println("Select number of problem:");
			System.out.println("1.- Problem 1:");
			System.out.println("2.- Problem 2:");
			System.out.println("3.- Problem 3: \n");
			System.out.println("------------------------\n");
			System.out.println("Choose problem: \n");
			numEx = 3;//CAMBIARRRR¡¡¡¡
			errorsTraining = new double[numEx];
			errorsValidation = new double[numEx];
			errorsTest = new double[numEx];
			valuesOverfit = new double[numEx];
			numEpochs = new double[numEx];
			numRelevantEpochs = new double[numEx];
			
			numberOfProblem = Integer.parseInt(br.readLine());
			switch(numberOfProblem)
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
			
			Backpropagation bk = new Backpropagation(neuralNetwork, 0.01, 0.05, dataTraining, dataValidation, numEx, errorTraining, errorValidation, errorTest, testSetClasification, overfit, totalEpochs, relevantEpochs );
		
			 int maxEpoch = 300;
			 double threshold = 0.003;
			 bk.exec(maxEpoch, threshold);
			 
			 errorsTest[cEx] = bk.getErrorTest();
			 errorsTraining[cEx] = bk.getErrorTraining();
			 errorsValidation[cEx] = bk.getErrorValidation();
			 valuesOverfit[cEx] = bk.getOverfit();
			 numEpochs[cEx] = bk.getTotalEpochs();
			 numRelevantEpochs[cEx] = bk.getRelevantEpochs();
			 
			 meanErrorTest = calculateMean(errorsTest, numEx);
			 meanErrorValidation = calculateMean(errorsValidation, numEx);
			 meanErrorTraining = calculateMean(errorsTraining, numEx);
			 meanOverfit = calculateMean(valuesOverfit, numEx);
			 meanEpochs = calculateMean(numEpochs, numEx);
			 meanRelevantEpochs = calculateMean(numRelevantEpochs, numEx);
			 
			 stddevTest = calculateStddev(errorsTest, meanErrorTest, numEx);
			 stddevTraining = calculateStddev(errorsTraining, meanErrorTraining, numEx);
			 stddevValidation = calculateStddev(errorsValidation, meanErrorValidation, numEx);
			 stddevOverfit = calculateStddev(valuesOverfit, meanOverfit, numEx);
			 stddevEpochs = calculateStddev(numEpochs, meanEpochs, numEx);
			 stddevRelevantEpochs = calculateStddev(numRelevantEpochs, meanRelevantEpochs, numEx);
			 
			 //Math.rint(meanTraining * 100) / 100 );
			 
			 Pattern patternEq = Pattern.compile("/");
			 String[] problemRow = patternEq.split(problem);
			 
		
			 
			 String sFichero = "fichero.txt";
			 FileWriter fichero = new FileWriter("/Users/jesus/Desktop/" +sFichero);
			 
				 fichero.write("Problem: " + 	problemRow[1] + "\n");
				 fichero.write("\t Training Set \n");
				 fichero.write("\t\t Mean:" + meanErrorTraining + "\n");
				 fichero.write("\t\t Stddev" + stddevTraining + "\n");
				 fichero.write("\t Validation Set \n");
				 fichero.write("\t\t Mean" + meanErrorValidation + "\n");
				 fichero.write("\t\t Stddev" + stddevValidation + "\n");
				 fichero.write("\t Test Set \n");
				 fichero.write("\t\t" +meanErrorTest + "\n");
				 fichero.write("\t\t" + stddevTest+ "\n");
				 fichero.write("\t Test Set Clasification\n");
				// fichero.write("\t\t" +meanTestSetClafication + "\n");
				// fichero.write("\t\t" + stddevTestSetClafication+ "\n");
				 fichero.write("\t Overfit \n");
				 fichero.write("\t\t Mean" + meanOverfit + "\n");
				 fichero.write("\t\t Stddev" + stddevOverfit + "\n");
				 fichero.write("\t Total epochs \n");
				 fichero.write("\t\t Mean" + meanEpochs + "\n");
				 fichero.write("\t\t Stddev" + stddevEpochs + "\n");
				 fichero.write("\t Relevant epochs \n");
				 fichero.write("\t\t Mean" + meanRelevantEpochs + "\n");
				 fichero.write("\t\t Stddev" + stddevRelevantEpochs + "\n");
				 fichero.close();
			 
			 
			}//fin for;
			System.out.println("Espera");
		} catch (Exception e) {
			System.out.println("Algorithm.main" + e.toString());
		}

	}
	
	private static double calculateMean(double[] errorsTraining, int epochs) {
		double sum = 0.0;
		double mean = 0.0;

		try {
			for (int i = 0; i < epochs; i++)
				sum += errorsTraining[i];

			mean = (sum / epochs);
			return mean;
		} catch (Exception e) {
			System.out.println("Backpropagation.calculateMean" + e.toString());
			return 0.0;
		}

	}

	private static double calculateStddev(double[] errorsTraining, double mean,
			int epochs) {
		double sum = 0.0;
		double stddev = 0.0;

		try {
			for (int i = 0; i < epochs; i++)
				sum += Math.pow(errorsTraining[i] - mean, 2);

			stddev = sum / epochs;

			return stddev;
		} catch (Exception e) {
			System.out
					.println("Backpropagation.calculateStddev" + e.toString());
			return 0.0;
		}

	}

}

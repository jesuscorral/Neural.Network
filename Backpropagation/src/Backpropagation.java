/*
 * @author Jesús Corral Versión
 * @University Universidad de Sevilla
 * @Subject Inteligencia Artificial 2
 * @Proyect Backpropagation Algorithm with momentum and cross validation for Neural Network
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backpropagation {

	private NeuralNetwork neuralNetwork;
	private double learningRate;
	private double momentum;
	private DataTraining dataTraining;
	private DataValidation dataValidation;
	private DataTest dataTest;
	private int numEx;
	private double meanErrorTraining;
	private double stddevErrorTraining;
	private double meanErrorValidation;
	private double stddevErrorValidation;
	private double errorTest;
	private double testSetClasification;
	private double meanOverfit;
	private double stddevOverfit;
	private int totalEpochs;
	private int relevantEpochs;
	private boolean clasificationProblem;

	public Backpropagation(NeuralNetwork neuralNetwork, double learningRate,
			double momentum, DataTraining dataTraining,
			DataValidation dataValidation, DataTest dataTest, int numEx,
			double meanErrorTraining, double stddevErrorTraining,
			double meanErrorValidation, double stddevErrorValidation,
			double errorTest, double testSetClasification, double meanOverfit,
			double stddevOverfit, int totalEpochs, int relevantEpochs,
			boolean clasficationProblem) {
		this.neuralNetwork = neuralNetwork;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.dataTraining = dataTraining;
		this.dataValidation = dataValidation;
		this.dataTest = dataTest;
		this.numEx = numEx;
		this.meanErrorTraining = meanErrorTraining;
		this.stddevErrorTraining = stddevErrorTraining;
		this.meanErrorValidation = meanErrorValidation;
		this.stddevErrorValidation = stddevErrorValidation;
		this.errorTest = errorTest;
		this.testSetClasification = testSetClasification;
		this.meanOverfit = meanOverfit;
		this.stddevOverfit = stddevOverfit;
		this.totalEpochs = totalEpochs;
		this.relevantEpochs = relevantEpochs;
		this.setClasificationProblem(clasficationProblem);
	}

	public void exec(int maxEpoch, double threshold) {

		NeuralNetwork bestNeuralNetwork = null;
		int samplesTraining = dataTraining.getTraining_examples();
		int samplesValidation = dataValidation.getValidation_examples();
		int samplesTest = dataTest.getTest_examples();
		double lowestValidationError = Double.MAX_VALUE;
		boolean stop = false;
		double sumErrorTraining = 0.0;
		double minErrorTraining = Double.MAX_VALUE;
		double[][] outTrainingExpected = null, outTrainingCalculated = null;
		double[][] outValidationExpected = null, outValidationCalculated = null;
		double[][] outTestExpected = null, outTestCalculated = null;
		int currentEpoch = 0;
		int cont = 0;
		double[] errorsTraining = new double[maxEpoch];
		double[] errorsValidation = new double[maxEpoch / 5];
		double errorTraining = 0.0, errorValidation = 0.0;
		double[] overfits = new double[maxEpoch / 5];

		try {
			outTrainingExpected = new double[samplesTraining][dataTraining.getReal_out()];
			outTrainingCalculated = new double[samplesTraining][dataTraining.getReal_out()];

			outValidationExpected = new double[samplesValidation][dataValidation.getReal_out()];
			outValidationCalculated = new double[samplesValidation][dataValidation.getReal_out()];

			outTestExpected = new double[samplesTest][dataTest.getReal_out()];
			outTestCalculated = new double[samplesTest][dataTest.getReal_out()];

			// Initialize 0 for the next run
			currentEpoch = 0;
			lowestValidationError = Double.MAX_VALUE;
			stop = false;

			// 1.- Initialize weights
			for (Layer layer : neuralNetwork.getLayers()) {
				layer.initializeWeights();
			}

			do {
				// For each example Calculate the output of each unit transmitted values ​​forward
				for (int j = 0; j < samplesTraining; j++) {
					// Get the expected outputs
					outTrainingExpected[j] = dataTraining.getOutputs()[j];
					// Calculate the outputs from the inputs.
					outTrainingCalculated[j] = neuralNetwork.getOutputs(dataTraining, j);
					// Calculate the error.
					calculateErrorNeuron(outTrainingCalculated[j],outTrainingExpected[j]);
					// Updates the weights
					updateWeights(learningRate);
				}

				//Calculate and save the error training to calculate the mean and steddev
				errorTraining = errorMeasure(outTrainingExpected,outTrainingCalculated);
				errorsTraining[currentEpoch] = errorTraining;

				sumErrorTraining += errorTraining;
				if (errorTraining < minErrorTraining)
					minErrorTraining = errorTraining;

				// Each 5 epochs calculate and save the validation error
				if (currentEpoch % 5 == 0 && currentEpoch != 0) {
					for (int k = 0; k < samplesValidation; k++) {
						outValidationExpected[k] = dataValidation.getOutputs()[k];
						outValidationCalculated[k] = neuralNetwork.getOutputs(dataValidation, k);
					}

					errorValidation = errorMeasure(outValidationExpected,outValidationCalculated);
					errorsValidation[currentEpoch / 5] = errorValidation;
					cont++;

					if (errorValidation < lowestValidationError) {
						lowestValidationError = errorValidation;
						setRelevantEpochs(currentEpoch);
						bestNeuralNetwork = neuralNetwork;
					}

					// Calculate the overfit.
					overfits[currentEpoch / 5] = 100 * ((errorValidation / lowestValidationError) - 1);

					// Calculate the progress in the training
					int k = 5;
					double Pk = 0.0;

					Pk = 1000 * ((sumErrorTraining / (k * minErrorTraining)) - 1);
					sumErrorTraining = 0.0;
					minErrorTraining = Double.MAX_VALUE;

					System.out.println("Epoch: " + currentEpoch
							+ " || Error Validation: "
							+ Math.rint(errorValidation * 10000) / 10000
							+ " || Min Error Validatiom: "
							+ Math.rint(lowestValidationError * 10000) / 10000
							+ " || Gl: "
							+ Math.rint(overfits[currentEpoch / 5] * 10000)
							/ 10000 + " || Progress: " + Math.rint(Pk * 10000)
							/ 10000 + " || Error Training: "
							+ Math.rint(errorTraining * 10000) / 10000);

					/* Overfit is also evaluated only at the end of each training strip 
					if the overfit value is greather than threshold indicated by the user, stop the training. */
					if (overfits[currentEpoch / 5] > threshold)
						stop = true;
					
					/*If the progress is less than 0,1 stop the training*/
					if (Pk <= 0.1)
						stop = true;
				}

				currentEpoch++;
			} while (maxEpoch > currentEpoch && stop == false);

			/*After training exec the best neueral network save before with the data test examples*/
			execTest(bestNeuralNetwork, outTestExpected, outTestCalculated,samplesTest, dataTest);

			/*Calculate the mean and steddev*/
			double meanErrorTraining = calculateMean(errorsTraining,currentEpoch);
			double meanErrorValidation = calculateMean(errorsValidation, cont);
			double stddevErrorTraining = calculateStddev(errorsTraining,meanErrorTraining, currentEpoch);
			double stddevErrorValidation = calculateStddev(errorsValidation,meanErrorValidation, cont);
			double meanOverfit = calculateMean(overfits, cont);
			double stddevOverfit = calculateStddev(overfits, meanOverfit, cont);

			setMeanErrorTraining(meanErrorTraining);
			setMeanErrorValidation(meanErrorValidation);
			setErrorTest(errorMeasure(outTestExpected, outTestCalculated));
			setErrorTest(errorMeasure(outTestExpected, outTestCalculated));
			if (clasificationProblem) {
				setTestSetClasification(errorMeasureClasification(
						outTestExpected, outTestCalculated));
			}
			setStddevErrorTraining(stddevErrorTraining);
			setStddevErrorValidation(stddevErrorValidation);
			setMeanOverfit(meanOverfit);
			setStddevOverfit(stddevOverfit);
			setTotalEpochs(currentEpoch);

		} catch (Exception e) {
			System.out.println("Backpropagation.exec" + e.toString());
		}
	}

	private void execTest(NeuralNetwork neuralNetwork,
			double[][] outTestExpected, double[][] outTestCalculated,
			int samplesTest, DataTest dataTest) {
		for (int k = 0; k < samplesTest; k++) {
			outTestExpected[k] = dataTest.getOutputs()[k];
			outTestCalculated[k] = neuralNetwork.getOutputs(dataTest, k);
		}
	}
	
	private static double calculateMean(double[] errors, int epochs) {
		double sum = 0.0;
		double mean = 0.0;

		try {
			for (int i = 0; i < epochs; i++)
				sum += errors[i];

			mean = (sum / epochs);
			return mean;
		} catch (Exception e) {
			System.out.println("Backpropagation.calculateMean" + e.toString());
			return 0.0;
		}

	}

	private static double calculateStddev(double[] errors, double mean,
			int epochs) {
		double sum = 0.0;
		double stddev = 0.0;

		try {
			for (int i = 0; i < epochs; i++)
				sum += Math.pow(errors[i] - mean, 2);

			stddev = sum / epochs;
			stddev = Math.sqrt(stddev);
			return stddev;
		} catch (Exception e) {
			System.out
					.println("Backpropagation.calculateStddev" + e.toString());
			return 0.0;
		}

	}

	private void calculateErrorNeuron(double[] outputsCalculated, double[] expectedOutputs) {
	
		List<Layer> layers = neuralNetwork.getLayers();
		
		try {
			for (int i = layers.size() - 1; i >= 0; i--) {
				Layer layer = layers.get(i);
				for (int j = 0; j < layer.getNeurons().size(); j++) {
					double errorN = 0;
					Neuron n = layer.getNeurons().get(j);
					// Last layer
					if (layer.isOutputLayer()) {
						// ValueRetropropagtion is the value calculated
						// previously = Ai(1-Ai)
						errorN = n.getOutput() * (1 - n.getOutput()) * (expectedOutputs[j] - outputsCalculated[j]);
						n.setError(errorN);
					}
					// Next Layers
					else {
						double sum = 0.0;
						errorN = n.getOutput() * (1 - n.getOutput());
						// ValueRetropropagtion is the value calculated
						// previously = Ai(1-Ai)
						List<Neuron> neuronsNextLayer = layer.getNextlayer()
								.getNeurons();
						for (Neuron neuronNextLayer : neuronsNextLayer) {
							List<Synapse> inputSynNextLayer = neuronNextLayer.getInputSynapses();
							for (Synapse s : inputSynNextLayer) {
								if (s.getSourceNeuron() == neuronNextLayer) {
									sum += s.getWeight() * neuronNextLayer.getError();
								}
							}
						}
						errorN *= sum;
						n.setError(errorN);
					}
				}
			}
		} catch (RuntimeException e) {
			System.out.println("Backpropagation.calculateError" + e.toString());
		}
	}

	private void updateWeights(double learningRate) {
		Map<Synapse, Double> synapsesUpdatesPrevious = new HashMap<Synapse, Double>();

		List<Layer> layers = neuralNetwork.getLayers();
		double weight = 0.0;
		try {
			for (int j = layers.size() - 1; j >= 0; j--) {
				Layer layer = layers.get(j);

				for (Neuron neuronCurrent : layer.getNeurons()) {
					List<Synapse> synapses = neuronCurrent.getInputSynapses();
					for (int i = 0; i < synapses.size(); i++) {
						Synapse synapse = synapses.get(i);

						weight = synapse.getWeight();
						Neuron neuronPrev = synapse.getSourceNeuron();
						double delta = learningRate * neuronPrev.getOutput()
								* neuronCurrent.getError();

						// Para la n-esima actualizacion se tienen que tener en
						// cuenta el valor del delta
						// anterior, por tanto para n = 1 como no hay delta en
						// el hasmap, lo almacenamos, para en las siguientes
						// iteracciones utilizar este valor y almacenar el
						// actual.
						if (synapsesUpdatesPrevious.get(synapse) != null) {
							double previousDelta = synapsesUpdatesPrevious.get(synapse);
							delta += momentum * previousDelta;
						}

						synapsesUpdatesPrevious.put(synapse, delta);

						weight += delta;
						synapse.setWeight(weight);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Backpropagation.updateWeights" + e.toString());
		}
	}

	private double errorMeasure(double[][] realOutputs,
			double[][] expectedOutputs) {
		double error = 0.0;
		double min = findOutputMin(realOutputs);
		double max = findOutputMax(realOutputs);
		int N = realOutputs.length;
		int P = realOutputs[0].length;
		double sum = 0.0;

		try {
			for (int i = 0; i < N; i++)
				for (int j = 0; j < P; j++) {
					sum += Math.pow(expectedOutputs[i][j] - realOutputs[i][j], 2);
				}
			error = 100 * ((max - min) / (N * P)) * sum;
			return error;
		} catch (Exception e) {
			System.out.println("Backpropagation.errorMeasure" + e.toString());
			return 0.0;
		}
	}

	private double errorMeasureClasification(double[][] realOutputs, double[][] expectedOutputs) {
		double error = 0.0;
		int N = realOutputs.length;
		int P = realOutputs[0].length;
		int sum = 0;

		double roundExpectedOutput = 0.0;
		double roundRealOutput = 0.0;

		try {
			for (int i = 0; i < N; i++)
				for (int j = 0; j < P; j++) {
					if (expectedOutputs[i][j] <= 0.5)
						roundExpectedOutput = 0;
					else
						roundExpectedOutput = 1;

					if (realOutputs[i][j] <= 0.5)
						roundRealOutput = 0;
					else
						roundRealOutput = 1;

					if (roundExpectedOutput == roundRealOutput)
						sum++;
				}
			error = (sum * 100) / (N * P);
			return error;
		} catch (Exception e) {
			System.out.println("Backpropagation.errorMeasure" + e.toString());
			return 0.0;
		}
	}

	private double findOutputMax(double[][] realOutputs) {
		double max = -10.0;
		int examples = realOutputs.length;
		int outputs = realOutputs[0].length;

		try {
			for (int i = 0; i < examples; i++)
				for (int j = 0; j < outputs; j++) {
					if (realOutputs[i][j] > max)
						max = realOutputs[i][j];
				}
			return max;
		} catch (Exception e) {
			System.out.println("Backpropagation.findOutputMax" + e.toString());
			return 0.0;
		}

	}

	private double findOutputMin(double[][] realOutputs) {
		double min = 100.0;
		int examples = realOutputs[0].length;
		int outputs = realOutputs[0].length;

		try {
			for (int i = 0; i < examples; i++)
				for (int j = 0; j < outputs; j++) {
					if (realOutputs[i][j] < min)
						min = realOutputs[i][j];
				}
			return min;

		} catch (Exception e) {
			System.out.println("Backpropagation.findOutputMin" + e.toString());
			return 0.0;
		}
	}

	
	/*Getters and Setters*/
	public NeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}

	public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getMomentum() {
		return momentum;
	}

	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}

	public DataTraining getDataTraining() {
		return dataTraining;
	}

	public void setDataTraining(DataTraining dataTraining) {
		this.dataTraining = dataTraining;
	}

	public DataValidation getDataValidation() {
		return dataValidation;
	}

	public void setDataValidation(DataValidation dataValidation) {
		this.dataValidation = dataValidation;
	}

	public int getNumEx() {
		return numEx;
	}

	public void setNumEx(int numEx) {
		this.numEx = numEx;
	}

	public double getErrorTest() {
		return errorTest;
	}

	public void setErrorTest(double errorTest) {
		this.errorTest = errorTest;
	}

	public double getTestSetClasification() {
		return testSetClasification;
	}

	public void setTestSetClasification(double testSetClasification) {
		this.testSetClasification = testSetClasification;
	}

	public int getTotalEpochs() {
		return totalEpochs;
	}

	public void setTotalEpochs(int totalEpochs) {
		this.totalEpochs = totalEpochs;
	}

	public int getRelevantEpochs() {
		return relevantEpochs;
	}

	public void setRelevantEpochs(int relevantEpochs) {
		this.relevantEpochs = relevantEpochs;
	}

	public double getMeanErrorTraining() {
		return meanErrorTraining;
	}

	public void setMeanErrorTraining(double meanErrorTraining) {
		this.meanErrorTraining = meanErrorTraining;
	}

	public double getMeanErrorValidation() {
		return meanErrorValidation;
	}

	public void setMeanErrorValidation(double meanErrorValidation) {
		this.meanErrorValidation = meanErrorValidation;
	}

	public double getStddevErrorTraining() {
		return stddevErrorTraining;
	}

	public void setStddevErrorTraining(double stddevErrorTraining) {
		this.stddevErrorTraining = stddevErrorTraining;
	}

	public double getStddevErrorValidation() {
		return stddevErrorValidation;
	}

	public void setStddevErrorValidation(double stddevErrorValidation) {
		this.stddevErrorValidation = stddevErrorValidation;
	}

	public double getMeanOverfit() {
		return meanOverfit;
	}

	public void setMeanOverfit(double meanOverfit) {
		this.meanOverfit = meanOverfit;
	}

	public double getStddevOverfit() {
		return stddevOverfit;
	}

	public void setStddevOverfit(double stddevOverfit) {
		this.stddevOverfit = stddevOverfit;
	}

	public boolean isClasificationProblem() {
		return clasificationProblem;
	}

	public void setClasificationProblem(boolean clasificationProblem) {
		this.clasificationProblem = clasificationProblem;
	}

}

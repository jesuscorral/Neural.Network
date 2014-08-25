import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backpropagation {

	private NeuralNetwork neuralNetwork;
	private double learningRate;
	private double momentum;
	private DataTraining dataTraining;
	private DataValidation dataValidation;
	private int numEx;
	private double errorTraining;
	private double errorValidation;
	private double errorTest;
	private double testSetClasification;
	private double overfit;
	private double totalEpochs;
	private double relevantEpochs;

	
	public Backpropagation(NeuralNetwork neuralNetwork, double learningRate,
			double momentum, DataTraining dataTraining,
			DataValidation dataValidation, int numEx, double errorTraining, double errorValidation,
			double errorTest, double testSetClasification, double overfit, double totalEpochs, double relevantEpochs) {
		this.neuralNetwork = neuralNetwork;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.dataTraining = dataTraining;
		this.dataValidation = dataValidation;
		this.numEx = numEx;
		this.errorTraining = errorTraining;
		this.errorValidation = errorValidation;
		this.errorTest = errorTest;
		this.testSetClasification = testSetClasification;
		this.overfit = overfit;
		this.totalEpochs = totalEpochs;
		this.relevantEpochs = relevantEpochs;
		
	}

	public void exec(int maxEpoch, double threshold) {
		
		int currentEpoch = 0;
		int samplesTraining = dataTraining.getTraining_examples();
		int samplesValidation = dataValidation.getValidation_examples();
		double errorTrainingPrevious = 0.0;
		double lowestValidationError = Double.MAX_VALUE;
	//	double gl = 0.0;
		boolean stop = false;
		double sumErrorTraining = 0.0;
		double minErrorTrianing = Double.MAX_VALUE;
		// Variables utilizadas para saber si las entradas/salidas son
		// reales(continuous) o boolenas(discrete). True=Real, False=Boolean
		double[][] outTrainingExpected = null;
		double[][] outTrainingCalculated = null;
		double[][] outValidationExpected = null;
		double[][] outValidationCalculated = null;
			
		try {

			outTrainingExpected = new double[samplesTraining][dataTraining.getReal_out()];
			outTrainingCalculated = new double[samplesTraining][dataTraining.getReal_out()];

			outValidationExpected = new double[samplesValidation][dataValidation.getReal_out()];
			outValidationCalculated = new double[samplesValidation][dataValidation.getReal_out()];



				// Initialize 0 for the next run
				currentEpoch = 0;
				errorTrainingPrevious = 0.0;
				lowestValidationError = Double.MAX_VALUE;
				stop = false;
				sumErrorTraining = 0.0;
				minErrorTrianing = Double.MAX_VALUE;
				
				// 1.- Initialize weights
				for (Layer layer : neuralNetwork.getLayers()) {
					layer.initializeWeights();
				}
				
				do {
					// 2.1 Para cada ejemplo (x,y) D Calcular la salida de cada
					// unidad propagando valores hacia adelante
					for (int j = 0; j < samplesTraining; j++) {
						outTrainingExpected[j] = dataTraining.getOutputs()[j];
						// 2.1.1. Calcular cada salida
						outTrainingCalculated[j] = neuralNetwork.getOutputs(dataTraining, j);
						// 2.2.1. Calcular el error
						calculateErrorNeuron(outTrainingCalculated[j],outTrainingExpected[j]);
						// 2.2.2 Actualizamos los pesos una vez por epoch
						updateWeights(learningRate);
					}

					setErrorTraining(errorMeasure(outTrainingExpected,outTrainingCalculated));
					
					sumErrorTraining += getErrorTraining();
					if (getErrorTraining() < minErrorTrianing)
						minErrorTrianing = getErrorTraining();

					// Actualizamos cada 5 epochs
					if (currentEpoch % 5 == 0 && currentEpoch != 0) {
						for (int k = 0; k < samplesValidation; k++) {
							outValidationExpected[k] = dataValidation.getOutputs()[k];
							outValidationCalculated[k] = neuralNetwork.getOutputs(dataValidation, k);
						}

						setErrorValidation(errorMeasure(outValidationExpected,outValidationCalculated));

						if (getErrorValidation() < lowestValidationError)
						{
							lowestValidationError = getErrorValidation();
							setRelevantEpochs(currentEpoch);
						}
							
						//gl = overfit;
					setOverfit( 100*((getErrorValidation() / lowestValidationError) - 1));

						// Controlamos Pk
						int k = 5;
						double Pk = 0.0;

						Pk = 1000 * ((sumErrorTraining / (k * minErrorTrianing)) - 1);

						System.out.println("Epoch: " + currentEpoch
								+ " // ErrorValidation: "
								+ Math.rint(getErrorValidation() * 10000) / 10000
								+ " // Minimo error: "
								+ Math.rint(lowestValidationError * 10000)
								/ 10000 + " // Gl: " + Math.rint(getOverfit() * 10000)
								/ 10000 + " // Progress: "
								+ Math.rint(Pk * 10000) / 10000 + "Error Train:"+ getErrorTraining());

						// Gl is also evaluated only at the end of each training
						// strip
						if (getOverfit() > (100*threshold))
							stop = true;

						if (Pk <= 0.0)
							stop = true;

						sumErrorTraining = 0.0;
						minErrorTrianing = Double.MAX_VALUE;
					}

					if (currentEpoch == 0) {
						errorTrainingPrevious = getErrorTraining();
					}

					if ((errorTrainingPrevious - getErrorTraining()) / 1000 > 0.1)
						stop = true;

					currentEpoch++;

				} while (maxEpoch > currentEpoch && stop == false);

				setTotalEpochs(currentEpoch);
				setErrorTraining(minErrorTrianing);				
				setErrorValidation(lowestValidationError);

		} catch (Exception e) {
			System.out.println("Backpropagation.exec" + e.toString());
		}
	}


	// Pone en cada neuron su error
	private void calculateErrorNeuron(double[] outputsCalculated,
			double[] expectedOutputs) {
		List<Layer> layers = neuralNetwork.getLayers();
		// int numLayers =
		try {

			for (int i = layers.size() - 1; i >= 0; i--) {
				// for (int i = 1; i >= 0; i--) {
				Layer layer = layers.get(i);
				for (int j = 0; j < layer.getNeurons().size(); j++) {
					double errorN = 0;
					Neuron n = layer.getNeurons().get(j);
					// Last layer
					if (layer.isOutputLayer()) {
						// ValueRetropropagtion is the value calculated
						// previously = Ai(1-Ai)
						errorN = n.getOutput() * (1 - n.getOutput())
								* (expectedOutputs[j] - outputsCalculated[j]);
						// errorN = n.getValueRetropropagtion() *
						// (expectedOutputs[j] - outputsCalculated[j]);
						n.setError(errorN);
					}
					// Next Layers
					else {
						double sum = 0.0;
						// errorN = n.getValueRetropropagtion();
						errorN = n.getOutput() * (1 - n.getOutput());
						// ValueRetropropagtion is the value calculated
						// previously = Ai(1-Ai)
						List<Neuron> neuronsNextLayer = layer.getNextlayer()
								.getNeurons();
						for (Neuron neuronNextLayer : neuronsNextLayer) {
							List<Synapse> inputSynNextLayer = neuronNextLayer
									.getInputSynapses();
							for (Synapse s : inputSynNextLayer) {
								if (s.getSourceNeuron() == neuronNextLayer) {
									sum += s.getWeight()
											* neuronNextLayer.getError();
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
							double previousDelta = synapsesUpdatesPrevious
									.get(synapse);
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
					// sum += Math.pow(realOutputs[i][j] -
					// expectedOutputs[i][j], 2);
					sum += Math.pow(expectedOutputs[i][j] - realOutputs[i][j],
							2);
				}

			error = 100 * ((max - min) / (N * P)) * sum;
			error = error * 0.5;
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

	public double getErrorTraining() {
		return errorTraining;
	}

	public void setErrorTraining(double errorTraining) {
		this.errorTraining = errorTraining;
	}

	public double getErrorValidation() {
		return errorValidation;
	}

	public void setErrorValidation(double errorValidation) {
		this.errorValidation = errorValidation;
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

	public double getOverfit() {
		return overfit;
	}

	public void setOverfit(double overfit) {
		this.overfit = overfit;
	}

	public double getTotalEpochs() {
		return totalEpochs;
	}

	public void setTotalEpochs(double totalEpochs) {
		this.totalEpochs = totalEpochs;
	}

	public double getRelevantEpochs() {
		return relevantEpochs;
	}

	public void setRelevantEpochs(double relevantEpochs) {
		this.relevantEpochs = relevantEpochs;
	}

}

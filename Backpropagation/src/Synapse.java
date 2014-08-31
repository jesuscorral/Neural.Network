/*
 * @author Jesús Corral Versión
 * @University Universidad de Sevilla
 * @Subject Inteligencia Artificial 2
 * @Proyect Backpropagation Algorithm with momentum and cross validation for Neural Network
 */

/*The synapse is the input signal to the attenuated or amplified 
 * through the synapse weight neuron.
 */

public class Synapse {
	private Neuron sourceNeuron;
	private double weight;

	public Synapse(Neuron sourceNeuron, double weight) {
		super();
		this.sourceNeuron = sourceNeuron;
		this.weight = weight;
	}

	public Synapse() {
		super();
	}

	public Neuron getSourceNeuron() {
		return sourceNeuron;
	}

	public void setSourceNeuron(Neuron sourceNeuron) {
		this.sourceNeuron = sourceNeuron;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}

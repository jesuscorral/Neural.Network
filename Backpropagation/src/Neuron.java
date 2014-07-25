import java.util.ArrayList;
import java.util.List;

/*
 * A neuron may have one or more inputs,  one output which depend on the activation function. 
 * There's a artificial input which we use to normalize the output. Depending on the 
 * inputs and activation function, the neuron can be triggered or can't be triggered.
 */

public class Neuron {

	private ActivationFunction af;
	private double output = 0;
	private double weight;
	private List<Synapse> inputSynapses;
	
	private double error;
	private double derivative;

	public ActivationFunction getAf() {
		return af;
	}

	public void setAf(ActivationFunction af) {
		this.af = af;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public List<Synapse> getInputSynapses() {
		return inputSynapses;
	}

	public void setInputSynapses(List<Synapse> inputSynapses) {
		this.inputSynapses = inputSynapses;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public double getDerivative() {
		return derivative;
	}

	public void setDerivative(double derivative) {
		this.derivative = derivative;
	}

	// Every neuron may have one or more synapses, one by each input.
	public Neuron(ActivationFunction activationFunction) {
		setInputSynapses(new ArrayList<Synapse>());
		setAf(activationFunction);
		// af = activationFunction;
		setError(error);
		// error = 0;
	}

	// Synapse is weight between a neuron and the next neuron
	public void AddInputSynapse(Synapse input) {
		inputSynapses.add(input);
	}

  

}

/*
 * @author Jesús Corral Versión
 * @University Universidad de Sevilla
 * @Subject Inteligencia Artificial 2
 * @Proyect Backpropagation Algorithm with momentum and cross validation for Neural Network
 */

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
	private boolean booloutput = false;
	private double weight;
	private List<Synapse> inputSynapses;
	private double error;
	private double valueRetropropagtion;
	
	
	// Every neuron may have one or more synapses, one by each input.
	public Neuron(ActivationFunction activationFunction) {
		setInputSynapses(new ArrayList<Synapse>());
		setAf(activationFunction);
		setError(error);
	}

	// Synapse is weight between a neuron and the next neuron
	public void AddInputSynapse(Synapse input) {
		inputSynapses.add(input);
	}

	public void calculateOutputNeuron()
	{
		calculateWeight();
		output = af.valueActivation(weight);
		valueRetropropagtion = af.valueRetropropagation(output);
    }

    private void calculateWeight() {
        weight = 0;
        for (Synapse synapse : inputSynapses) {
            weight += synapse.getWeight() * synapse.getSourceNeuron().getOutput();
        }
    }

    /*Getters and Setters*/
	public boolean getBooloutput() {
		return booloutput;
	}

	public void setBooloutput(boolean booloutput) {
		this.booloutput = booloutput;
	}

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

	public double getValueRetropropagtion() {
		return valueRetropropagtion;
	}

	public void setValueRetropropagtion(double valueRetropropagtion) {
		this.valueRetropropagtion = valueRetropropagtion;
	}

}

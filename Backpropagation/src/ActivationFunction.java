// TODO: Auto-generated Javadoc
/*
 * @author Jesús Corral Versión
 * @University Universidad de Sevilla
 * @Subject Inteligencia Artificial 2
 * @Proyect Backpropagation Algorithm with momentum and cross validation for Neural Network
 */


/**
 * The Class ActivationFunction use the "Sigmoide Activation Function" 
 */
public class ActivationFunction {
	
	double e;	
	double p;

	public ActivationFunction(){
		super();
	}

	/**
	 * Value activation. Is used to calculate the neuron output.
	 * 
	 * @param weight
	 *            Weight between two neurons
	 * @return the double
	 */
	public double valueActivation(double weight) {
        return 1.0 / (1 + Math.exp(-1.0 * weight));
    }

	/**
	 * Value retropropagation is used to calculate the error of the neurons
	 * 
	 * @param output
	 *            Of the neurons
	 * @return the double
	 */
	public double valueRetropropagation (double output) {
        return output * (1.0 - output);
    }
}

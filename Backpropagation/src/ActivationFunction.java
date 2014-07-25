public class ActivationFunction {

	public ActivationFunction() {
		super();
		
	}
	
	public double valueActivation(double weight) {
		
        return 1.0 / (1 + Math.exp(-1.0 * weight));
    }


	
}

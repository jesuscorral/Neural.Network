public class ActivationFunction {

	double e;
	double p;
	
/*	public ActivationFunction() {
		e = Math.E; 
		p = 1;
	//	super();
		
	}*/
	
	public ActivationFunction(){
		
	}
	
/*	public double sigmoide(double a)
	{
		
		return (1/(1+ Math.pow(e, -a/p)));
	}*/
	
	public double valueActivation(double weight) {
		
        return 1.0 / (1 + Math.exp(-1.0 * weight));
    }

	public double valueRetropropagation (double output) {
        return output * (1.0 - output);
    }


	
}

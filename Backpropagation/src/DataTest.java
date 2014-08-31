/*
 * @author Jesús Corral Versión
 * @University Universidad de Sevilla
 * @Subject Inteligencia Artificial 2
 * @Proyect Backpropagation Algorithm with momentum and cross validation for Neural Network
 */

import java.util.ArrayList;
import java.util.List;

public class DataTest extends Data {

	private double inputs[][];
	private double outputs[][];

	public void LoadDataTest(String path) {
		List<String[]> data = new ArrayList<String[]>();

		data = LoadCommonData(path);

		LoadInputs(data);
		LoadOutputs(data);
	}

	private void LoadInputs(List<String[]> data) {
		int start = getHeadLines() + getTraining_examples() + getValidation_examples();
		int end = getHeadLines() + getTraining_examples()+ getValidation_examples() + getTest_examples();
		int numIn;
		
		if(getReal_in() == 0)
			numIn = getBool_in();
		else
			numIn = getReal_in();

		inputs = new double[data.size()][numIn];

		try {
			for (int i = start; i < end; i++)
				for (int j = 0; j < numIn; j++) {
					inputs[i - start][j] = Double.parseDouble(data.get(i)[j]);
				}
		} catch (Exception e) {
			System.out.println("DataTest.LoadInputReal" + e.toString());
		}

	}
	
	private void LoadOutputs(List<String[]> data) {
		int start = getHeadLines() + getTraining_examples()+ getValidation_examples();
		int end = getHeadLines() + getTraining_examples()+ getValidation_examples() + getTest_examples();
		int index2, numOuts;
		/*
		 * The second index (to save the output)depend of the input. We must
		 * start to save the value for the output apart from of the end of the
		 * inputs.
		 */
		if (getReal_in() == 0)
			index2 = getBool_in();
		else
			index2 = getReal_in();

		/*Check if the output is Real or Boolean to get correctly the number of outputs*/
		if(getReal_out() == 0)
			numOuts = getBool_out();
		else
			numOuts = getReal_out();
		
		outputs = new double[data.size()][numOuts];

		try {
			for (int i = start; i < end; i++)
				for (int j = index2; j < (index2 + numOuts); j++) {
					outputs[i - start][j - index2] = Double.parseDouble(data.get(i)[j]);
				}
		} catch (Exception e) {
			System.out.println("DataTest.LoadOutputReal" + e.toString());
		}

	}
	
	/*Getters and Setters*/ 
	public double[][] getInputs() {
		return inputs;
	}

	public void setInputs(double[][] inputs) {
		this.inputs = inputs;
	}

	public double[][] getOutputs() {
		return outputs;
	}

	public void setOutputs(double[][] outputs) {
		this.outputs = outputs;
	}

}

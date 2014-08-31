/*
 * @author Jesús Corral Versión
 * @University Universidad de Sevilla
 * @Subject Inteligencia Artificial 2
 * @Proyect Backpropagation Algorithm with momentum and cross validation for Neural Network
 */

import java.util.ArrayList;
import java.util.List;

public class DataTraining extends Data {

	private double inputs[][];
	private double outputs[][];

		
	public void LoadDataTraining(String path) {
		List<String[]> data = new ArrayList<String[]>();

		try {
			data = LoadCommonData(path);	
			LoadInputs(data);
			LoadOutputs(data);
		} catch (Exception e) {
			System.out.println("DataTraining.LoadDataTraining ->" + e.toString());
		}
	}


	private void LoadInputs(List<String[]> data) {
		int start = getHeadLines();
		int end = getHeadLines() + getTraining_examples();
		int numIn;
		
		if(getReal_in() == 0)
			numIn = getBool_in();
		else
			numIn = getReal_in();

		inputs = new double[getTraining_examples()][numIn];

		try {
			for (int i = start; i < end; i++)
				for (int j = 0; j < numIn; j++) {
					inputs[i - start][j] = Double.parseDouble(data.get(i)[j]);
				}
		} catch (Exception e) {
			System.out.println("DataTraining.LoadInputs ->" + e.toString());
		}
	}

	
	private void LoadOutputs(List<String[]> data) {
		int inicio = getHeadLines();
		int fin = getHeadLines() + getTraining_examples();
		int inicio2, numOuts;
		/*
		 * The second index (to save the output)depend of the input. We must
		 * start to save the value for the output apart from of the end of the
		 * inputs.
		 */
		if (getReal_in() == 0)
			inicio2 = getBool_in();
		else
			inicio2 = getReal_in();

		/*Check if the output is Real or Boolean to get correctly the number of outputs*/
		if(getReal_out() == 0)
			numOuts = getBool_out();
		else
			numOuts = getReal_out();
		
		outputs = new double[getTraining_examples()][numOuts];

		try {
			for (int i = inicio; i < fin; i++)
				for (int j = inicio2; j < (inicio2 + numOuts); j++) {
					outputs[i - inicio][j-inicio2] = Double.parseDouble(data.get(i)[j]);
				}
		} catch (Exception e) {
			System.out.println("DataTraining.LoadOutputs" + e.toString());
		}
	}

	/*Getters and Setters*/
	public double[][] getOutputs() {
		return outputs;
	}

	public void setOutputs(double outputs[][]) {
		this.outputs = outputs;
	}

	public double[][] getInputs() {
		return inputs;
	}

	public void setInputs(double inputs[][]) {
		this.inputs = inputs;
	}

}

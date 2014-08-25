import java.util.ArrayList;
import java.util.List;

public class DataTest extends Data {

	private double inputs[][];
	private double outputs[][];

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

	public void LoadDataTest(String path) {
		List<String[]> data = new ArrayList<String[]>();

		data = LoadCommonData(path);

		LoadInputs(data);
		LoadOutputs(data);
	}

	private void LoadInputs(List<String[]> data) {
		int inicio = getHeadLines() + getTraining_examples()
				+ getValidation_examples();
		int fin = getHeadLines() + getTraining_examples()+ getValidation_examples() + getTest_examples();
		int numIn;
		
		if(getReal_in() == 0)
			numIn = getBool_in();
		else
			numIn = getReal_in();

		inputs = new double[data.size()][numIn];

		try {
			for (int i = inicio; i < fin; i++)
				for (int j = 0; j < numIn; j++) {
					inputs[i - inicio][j] = Double.parseDouble(data.get(i)[j]);
				}
		} catch (Exception e) {
			System.out.println("DataTest.LoadInputReal" + e.toString());
		}

	}
	private void LoadOutputs(List<String[]> data) {
		int inicio = getHeadLines() + getTraining_examples()+ getValidation_examples();
		int fin = getHeadLines() + getTraining_examples()+ getValidation_examples() + getTest_examples();
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

		//Comprueba si la salida es Real o Boolena para cargar el numero de salidas correspondiente
		if(getReal_out() == 0)
			numOuts = getBool_out();
		else
			numOuts = getReal_out();
		
		outputs = new double[data.size()][numOuts];

		try {
			for (int i = inicio; i < fin; i++)
				for (int j = inicio2; j < (inicio2 + numOuts); j++) {
					outputs[i - inicio][j - inicio2] = Double.parseDouble(data.get(i)[j]);
				}
		} catch (Exception e) {
			System.out.println("DataTest.LoadOutputReal" + e.toString());
		}

	}
}

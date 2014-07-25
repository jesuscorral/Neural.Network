import java.util.ArrayList;
import java.util.List;

public class DataTraining extends Data {

	private double realInputs[][];
	private double realOutputs[][];
	private boolean bolInputs[][];
	private boolean bolOutputs[][];

	public double[][] getRealInputs() {
		return realInputs;
	}

	public void setRealInputs(double[][] realInputs) {
		this.realInputs = realInputs;
	}

	public double[][] getRealOutputs() {
		return realOutputs;
	}

	public void setRealOutputs(double[][] realOutputs) {
		this.realOutputs = realOutputs;
	}

	public boolean[][] getBolInputs() {
		return bolInputs;
	}

	public void setBolInputs(boolean[][] bolInputs) {
		this.bolInputs = bolInputs;
	}

	public boolean[][] getBolOutputs() {
		return bolOutputs;
	}

	public void setBolOutputs(boolean[][] bolOutputs) {
		this.bolOutputs = bolOutputs;
	}

	public void LoadDataTraining(String path) {
		List<String[]> data = new ArrayList<String[]>();

		data = LoadCommonData(path);

		if (getBool_in() != 0) {
			LoadInputBoolean(data);
		}
		if (getBool_out() != 0) {
			LoadOutputBoolean(data);
		}
		if (getReal_in() != 0) {
			LoadInputReal(data);
		}
		if (getReal_out() != 0) {
			LoadOutputReal(data);
		}
	}

	private void LoadInputReal(List<String[]> dataReal) {
		int inicio = getHeadLines();
		int fin = getHeadLines() + getTraining_examples();

		realInputs = new double[dataReal.size()][getReal_in()];

		try {
			// System.out.println("Cargando real inputs");
			for (int i = inicio; i < fin; i++)
				for (int j = 0; j < getReal_in(); j++) {
					realInputs[i - inicio][j] = Double.parseDouble(dataReal
							.get(i)[j]);
				}
		} catch (Exception e) {
			System.out.println("DataTraining.LoadInputReal" + e.toString());
		}
	}

	private void LoadOutputBoolean(List<String[]> dataBoolean) {
		int inicio = getHeadLines();
		int fin = getHeadLines() + getTraining_examples();
		int inicio2;
		/*
		 * The second index (to save the output)depend of the input. We must
		 * start to save the value for the output apart from of the end of the
		 * inputs.
		 */
		if (getReal_in() == 0)
			inicio2 = getBool_in();
		else
			inicio2 = getReal_in();

		bolOutputs = new boolean[dataBoolean.size()][dataBoolean.size()];

		try {
			for (int i = inicio; i < fin; i++)
				for (int j = inicio2; j < (inicio2 + getBool_out()); j++) {
					bolOutputs[i - inicio][j] = (dataBoolean.get(i)[j]) != null;
				}
		} catch (Exception e) {
			System.out.println("DataTraining.LoadOutputBoolean" + e.toString());
		}
	}

	private void LoadInputBoolean(List<String[]> dataBoolean) {
		int inicio = getHeadLines();
		int fin = getHeadLines() + getTraining_examples();

		bolInputs = new boolean[dataBoolean.size()][dataBoolean.size()];

		try {
			for (int i = inicio; i < fin; i++)
				for (int j = 0; j < getBool_in(); j++) {
					bolInputs[i - inicio][j] = (dataBoolean.get(i)[j]) != null;
				}
		} catch (Exception e) {
			System.out.println("DataTraining.LoadInputBoolean" + e.toString());
		}
	}

	private void LoadOutputReal(List<String[]> dataReal) {
		int inicio = getHeadLines();
		int fin = getHeadLines() + getTraining_examples();
		int inicio2;
		/*
		 * The second index (to save the output)depend of the input. We must
		 * start to save the value for the output apart from of the end of the
		 * inputs.
		 */
		if (getReal_in() == 0)
			inicio2 = getBool_in();
		else
			inicio2 = getReal_in();

		realOutputs = new double[dataReal.size()][dataReal.size()];

		try {
			for (int i = inicio; i < fin; i++)
				for (int j = inicio2; j < (inicio2 + getReal_out()); j++) {
					realOutputs[i - inicio][j] = Double.parseDouble(dataReal.get(i)[j]);
				}
		} catch (Exception e) {
			System.out.println("DataTraining.LoadOutputReal" + e.toString());
		}

	}

}

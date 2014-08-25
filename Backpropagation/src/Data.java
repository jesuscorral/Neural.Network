import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Data {

	private Integer bool_in = 0;
	private Integer real_in = 0;
	private Integer bool_out= 0;
	private Integer real_out= 0;
	private Integer training_examples= 0;
	private Integer validation_examples= 0;
	private Integer test_examples;
	private static final int HEAD_LINES = 7;
			
	public static int getHeadLines() {
		return HEAD_LINES;
	}

	public Data() {
		super();
	}

	public Integer getBool_in() {
		return bool_in;
	}

	public void setBool_in(Integer bool_in) {
		this.bool_in = bool_in;
	}

	public Integer getReal_in() {
		return real_in;
	}

	public void setReal_in(Integer real_in) {
		this.real_in = real_in;
	}

	public Integer getBool_out() {
		return bool_out;
	}

	public void setBool_out(Integer bool_out) {
		this.bool_out = bool_out;
	}

	public Integer getReal_out() {
		return real_out;
	}

	public void setReal_out(Integer real_out) {
		this.real_out = real_out;
	}

	public Integer getTraining_examples() {
		return training_examples;
	}

	public void setTraining_examples(Integer training_examples) {
		this.training_examples = training_examples;
	}

	public Integer getValidation_examples() {
		return validation_examples;
	}

	public void setValidation_examples(Integer validation_examples) {
		this.validation_examples = validation_examples;
	}

	public Integer getTest_examples() {
		return test_examples;
	}

	public void setTest_examples(Integer test_examples) {
		this.test_examples = test_examples;
	}


	/*
	 * Read the document and we save it in a List<String>
	 * */
	public List<String[]> LoadCommonData(String path) {
		BufferedReader buf = null;
		List<String[]> rows = null;
		Pattern pattern = Pattern.compile("(\\s+)");
		Pattern patternEq = Pattern.compile("=");
		List<String[]> rowsHead = new ArrayList<String[]>();
		
		try {
			buf = new BufferedReader(new FileReader(path));
			String line = null;
			rows = new ArrayList<String[]>();
			while ((line = buf.readLine()) != null) {
				line = line.trim();
				String[] row = pattern.split(line);
				rows.add(row);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		for(int i=0; i<HEAD_LINES; i++)
		{
		String[] row = patternEq.split(rows.get(i)[0]);
		rowsHead.add(row);
		}

		this.bool_in = Integer.parseInt(rowsHead.get(0)[1]);
		this.real_in = Integer.parseInt(rowsHead.get(1)[1]);
		this.bool_out = Integer.parseInt(rowsHead.get(2)[1]);
		this.real_out = Integer.parseInt(rowsHead.get(3)[1]);
		this.training_examples = Integer.parseInt(rowsHead.get(4)[1]);
		this.validation_examples = Integer.parseInt(rowsHead.get(5)[1]);
		this.test_examples  = Integer.parseInt(rowsHead.get(6)[1]);
		
		return rows;
	}

}
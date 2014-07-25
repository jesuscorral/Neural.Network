import java.util.ArrayList;
import java.util.List;

/*
 * Neural network is a system of programs and data structures that approximates 
 * the operation of the human brain. A RNA is composed by layers, at least input 
 * layer and output layer with possibilities to have more intermediate layers.
 *
 */

public class NeuralNetwork {

	private List<Layer> layers;
	private Layer input;
	private Layer output;

	public NeuralNetwork() {
		layers = new ArrayList<Layer>();
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	public Layer getInput() {
		return input;
	}


	public void setInput(Layer input) {
		this.input = input;
	}

	public Layer getOutput() {
		return output;
	}

	public void setOutput(Layer output) {
		this.output = output;
	}

	public void addLayer(Layer layer) {
		layers.add(layer);

		if (layers.size() == 1) {
			input = layer;
		}
		if (layers.size() > 1) {
			Layer previousLayer = layers.get(layers.size() - 2);
			previousLayer.setNextlayer(layer);
		}
		output = layer;
	}

	public double[][] getOutputs(DataTraining dataTraining)
	{
        double[][] outputs = new double[dataTraining.getTraining_examples()][output.getNeurons().size()];

        for(int i=0;i<dataTraining.getTraining_examples();i++)
        {
        	
        	for(Layer layer : layers)
            {
        		if(layer.getPreviouslayer() == null)
        		{
        			layer.addOutput(dataTraining.getRealOutputs()[i]);
        		}
        		else{
        		layer.calculateOutputLayer();
        		}
            }
        
        	for(int j=0;j< output.getNeurons().size();j++)
        	{
        		outputs[i][j] = output.getNeurons().get(j).getOutput();
        	}
        	
        }
        return outputs;
	}
}

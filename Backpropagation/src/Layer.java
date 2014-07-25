import java.util.ArrayList;
import java.util.List;

/*
 * Every layer of the RNA is composed by neurons
 */

public class Layer {

	private List<Neuron> neurons;
	private Layer nextlayer;
	private Layer previouslayer;

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	public Layer getNextlayer() {
		return nextlayer;
	}

	public void setNextlayer(Layer nextlayer) {
		this.nextlayer = nextlayer;
	}

	public Layer getPreviouslayer() {
		return previouslayer;
	}

	public void setPreviouslayer(Layer previouslayer) {
		this.previouslayer = previouslayer;
	}

	public Layer() {
		this.neurons = new ArrayList<Neuron>();
	}

	public Layer(Layer previousLayer) {
		this.neurons = new ArrayList<Neuron>();
		this.previouslayer = previousLayer;
	}

	
	// Add neuron to layer
	public void AddNeuron(Neuron neuron) {
		// Add neuron to layer
		neurons.add(neuron);
	}

	// Save the weight (-1,1) beetween new neuron of the current layer and every neuron form previous layer.
	public void initializeWeights()
	{
		if(previouslayer != null)
		{
			for (Neuron neuronCurrentLayer : this.getNeurons())
			{
				for(Neuron neuronPreviousLayer: previouslayer.getNeurons())
				{
					Synapse s = new Synapse(neuronPreviousLayer,(Math.random() * 1) - 0.5); 
					neuronCurrentLayer.AddInputSynapse(s);
				}
			}
		}
	}
	
	public void calculateOutput()
	{
		//Si es la input layer las entradas son las que tomamos del fichero
		if(previouslayer != null)
		{
		
		}
		//sino hay que calcular la entrada con la salida de la capa anterior.
		else
		{
			 for(int i = 0; i < neurons.size(); i++) {
		           // neurons.get(i).setOutput();;
		        }

		}
	}
}


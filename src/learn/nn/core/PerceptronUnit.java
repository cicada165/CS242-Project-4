package learn.nn.core;

/**
 * A PerceptronUnit is a Unit that uses a hard threshold
 * activation function.
 */
public class PerceptronUnit extends NeuronUnit {
	
	/**
	 * The activation function for a Perceptron is a hard 0/1 threshold
	 * at z=0. (AIMA Fig 18.7)
	 */
	@Override
	public double activation(double z) {
		if(z >= 0.0) {
			return 1.0;
		}else {
			return 0.0;
		}
	}
	
	/**
	 * Update this unit's weights using the Perceptron learning
	 * rule (AIMA Eq 18.7).
	 * Remember: If there are n input attributes in vector x,
	 * then there are n+1 weights including the bias weight w_0. 
	 */
	@Override
	//Use for single layer neuron network
	public void update(double[] x, double y, double alpha) {
		//The threshold function
		double t = this.h_w(x);
		
		//Update the bias weight w_0
		double w_0 = this.getWeight(0);
		w_0 = w_0 + alpha * (y - t);
		this.setWeight(0, w_0);
			
		//Update weight for each connection using the Perceptron learning rule
		for(int i = 0; i < x.length; i++) {
			double w = this.getWeight(i+1);
			w = w + alpha * (y - t) * x[i];
			this.setWeight(i+1, w);
		}
	}
}

package learn.nn.examples;

import java.io.IOException;
import java.util.List;

import learn.nn.core.Example;
import learn.nn.core.MultiLayerFeedForwardNeuralNetwork;

/**
 * Implements a fully-connected NeuralNetwork with two inputs, two hidden units, and two output units, for use
 * learning the XOR function (see XorExampleGenerator).
 * @see AIMA p. 730 (which doesn't really say that the network can learn that function but...)
 */
public class XorNN extends MultiLayerFeedForwardNeuralNetwork {
	
	protected static final int NUM_INPUTS = 2;
	protected static final int NUM_HIDDENS = 2;
	protected static final int NUM_OUTPUTS = 2;
	
	public XorNN() {
		// Single hidden layer of this size
		super(NUM_INPUTS, NUM_HIDDENS, NUM_OUTPUTS);
	}
	
	public static void main(String[] argv) throws IOException {
		int epochs = 100;
		double alpha = 0.10;
		int nexamples = 1000;
		XorExampleGenerator generator = new XorExampleGenerator();
		XorNN network = new XorNN();
		System.out.println("XorNN: Training on " + nexamples + " examples for " + epochs + " epochs, alpha=" + alpha);
		List<Example> trainingSet = generator.examples(nexamples);
		network.train(trainingSet, epochs, alpha);
		network.dump();
		List<Example> testingSet = generator.examples(nexamples);
		double accuracy = network.test(testingSet);
		System.out.println("XorNN: Testing on " + nexamples + " examples: accuracy=" + accuracy);
		System.out.println();
		System.out.println("Learning Curve testing on all training data");
		System.out.println("EPOCHS\tACCURACY");
		for (epochs=100; epochs <= 3000; epochs+=100) {
			List<Example> examples = generator.examples(nexamples);
			network.train(examples, epochs, alpha);
			accuracy = network.test(examples);
			System.out.format("%d\t%.3f\n",  epochs, accuracy);
		}
	}
	
}

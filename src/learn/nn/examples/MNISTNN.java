package learn.nn.examples;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import learn.nn.core.Example;
import learn.nn.core.MultiLayerFeedForwardNeuralNetwork;
import learn.nn.core.NeuralNetwork;
import learn.nn.core.NeuralNetworkListener;

/**
 * Implements a fully-connected NeuralNetwork for recognizing MNIST digits.
 * The network has 28*28 input units (one per pixel), 300 hidden units (or whatever),
 * and 10 output units, one for each digit (label).
 * On my MBP Mar 2018, this took about 9 minutes for one round (epoch) of training, which yielded
 * an accuracy of 41.77%.
 */
public class MNISTNN extends MultiLayerFeedForwardNeuralNetwork {
	
	protected static final int NUM_INPUTS = 28*28;
	protected static final int NUM_HIDDENS = 300;
	protected static final int NUM_OUTPUTS = 10;
	
	public MNISTNN() {
		// Single hidden layer of this size
		super(NUM_INPUTS, NUM_HIDDENS, NUM_OUTPUTS);
	}
	
	public static void main(String[] argv) throws IOException {
		int epochs = 100;
		double alpha = 0.10;
		MNISTNN network = new MNISTNN();
		System.out.println("MNIST: reading training data...");
		String DATADIR = "src/learn/nn/examples";
		List<Example> trainingSet = MNIST.read(DATADIR+"/train-images-idx3-ubyte", DATADIR+"/train-labels-idx1-ubyte");
		System.out.println("MNIST: reading testing data...");
		List<Example> testingSet = MNIST.read(DATADIR+"/t10k-images-idx3-ubyte", DATADIR+"/t10k-labels-idx1-ubyte");
		System.out.println("MNIST: training on " + trainingSet.size() + " examples for " + epochs + " epochs, alpha=" + alpha);
		System.out.println("MNIST: testing on " + testingSet.size() + " examples");
		System.out.println("EPOCH\tACC\tTIMEms\tHHMMSS");;
		network.addListener(new NeuralNetworkListener() {
			protected long startTime;
			public void trainingEpochStarted(NeuralNetwork network, int epoch) {
				startTime = new Date().getTime();
			}
			public boolean trainingEpochCompleted(NeuralNetwork network, int epoch) {
				//network.dump();
				long now = new Date().getTime(); // Stop the clock before testing accuracy
				double accuracy = network.test(testingSet);
				long elapsed = now - startTime;
				long s = elapsed / 1000;
				long h = s / (60*60);
				s -= h*60*60;
				long m = s / 60;
				s -= m*60;
				System.out.format("%d\t%.3f\t%d\t%02d:%02d:%02d\n", epoch, accuracy, elapsed, h, m, s);
				return true;
			}
		});
		network.train(trainingSet, epochs, alpha);
	}
	
}

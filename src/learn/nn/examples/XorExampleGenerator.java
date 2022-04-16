package learn.nn.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import learn.nn.core.Example;

/**
 * An XorExampleGenerator generates Examples consisting
 * of two input bits, each randomly set to 0 or 1,
 * and two output bits (the first is 1 if the XOR of the inputs is 0,
 * the second is 1 if the XOR is 1).
 * @see XorNN
 */
public class XorExampleGenerator {
	
	protected Random random = new Random();
	
	public Example nextExample() {
		Example example = new Example(2, 2);
		boolean b1 = random.nextBoolean();
		boolean b2 = random.nextBoolean();
		boolean result = (b1 != b2);
		double x1 = b1 ? 1.0 : 0.0;
		double x2 = b2 ? 1.0 : 0.0;
		double y1 = !result ? 1.0 : 0.0;
		double y2 = result ? 1.0 : 0.0;
		example.inputs[0] = x1;
		example.inputs[1] = x2;
		example.outputs[0] = y1;
		example.outputs[1] = y2;
		return example;
	}
	
	public List<Example> examples(int len) {
		List<Example> result = new ArrayList<Example>(len);
		for (int i=0; i < len; i++) {
			result.add(nextExample());
		}
		return result;
	}
}

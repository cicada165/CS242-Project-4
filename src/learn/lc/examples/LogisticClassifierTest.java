package learn.lc.examples;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import learn.lc.core.DecayingLearningRateSchedule;
import learn.lc.core.Example;
import learn.lc.core.LogisticClassifier;

/**
 * Program for testing the LogisticClassifier.
 */
public class LogisticClassifierTest extends JPanel{
	
	static Map<Integer, Double> m = new HashMap<>();
	
	/**
	 * Read the Data from the file with the given filename, create a
	 * LogisticClassifier with the appropriate number of inputs for the data,
	 * and train it on the data, printing its (1.0-squared-error/sample) after
	 * each step. 
	 */
	public static void test(String filename, int nsteps, double alpha) throws IOException {
		System.out.println("filename: " + filename);
		System.out.println("nsteps: " + nsteps);
		System.out.println("alpha: " + alpha);
		
		List<Example> examples = Data.readFromFile(filename);
		int ninputs = examples.get(0).inputs.length; 
		LogisticClassifier classifier = new LogisticClassifier(ninputs) {
			public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
				double oneMinusError = 1.0-squaredErrorPerSample(examples);
				System.out.println(stepnum + "\t" + oneMinusError);
				m.put(stepnum,oneMinusError);
				
			}
		};
		
		if (alpha > 0) {
			classifier.train(examples, nsteps, alpha);
		} else {
			classifier.train(examples, 100000, new DecayingLearningRateSchedule());
		}
		
		JFrame frame = new JFrame("Logistic Classifier");
	}
	
	/**
	 * Train a LogisticClassifier on a file of examples and
	 * print its (1.0-squared-error/sample) after each training step.
	 */
	public static void main(String[] argv) throws IOException {
		if (argv.length < 3) {
			System.out.println("usage: java LogisticClassifierTest data-filename nsteps alpha");
			System.out.println("       specify alpha=0 to use decaying learning rate schedule (AIMA p725)");
			System.exit(-1);
		}
		//First argument is the file name
		String filename = argv[0];
		//Second argument is the number of steps
		int nsteps = Integer.parseInt(argv[1]);
		//Third argument is the learning rate
		//Enter 0 to use decaying learning rate
		double alpha = Double.parseDouble(argv[2]);
		
		LogisticClassifierTest lcanvas = new LogisticClassifierTest();
		String prefix = "src/learn/lc/examples/";
		test(prefix+filename,nsteps,alpha);
		JFrame f = new JFrame("Graph");
		f.add(lcanvas);
		f.setSize(1200, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
	
	public void paintComponent(Graphics g) {

		int height=this.getHeight();
		int width=this.getWidth();
		int eachheight=height-200;
		
		for(int i=1;i<m.size();i++) {
			
			int y1=(int) (eachheight-eachheight*m.get(i)+100);
			int y2=(int) (eachheight-eachheight*m.get(i+1)+100);
			int x1=(int) (100+i*(width-200)/m.size());
			int x2=(int) (100+(i+1)*(width-200)/m.size());
            g.drawLine(x1,y1, x2,y2);
			
		}
		
		
		g.drawLine(100, 700, 100, 100);
		g.drawLine(1100, 700, 100, 700);
		g.drawString("0", 90, 710);
		
		g.drawString("1",75,105);
		g.drawString(Integer.toString(m.size()),1090,730);
		g.drawLine(1100,700,1100,710);
		g.drawLine(100, 100, 90, 100);
		
		for(int i=1;i<10;i++) {
			String r=Integer.toString(i);
			String size=Integer.toString(m.size()/10*i);
			g.drawString("0.",65, 700-i*60+5);
			g.drawString(r,75, 700-i*60+5);
			g.drawString(size, i*100+80,730 );
			g.drawLine(100+100*i, 700, 100+100*i, 710);
			g.drawLine(90, 100+60*i, 100, 100+60*i);
			
		}
	}
		

}

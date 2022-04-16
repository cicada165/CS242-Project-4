package learn.lc.examples;

import java.awt.Graphics;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import learn.lc.core.DecayingLearningRateSchedule;
import learn.lc.core.Example;
import learn.lc.core.PerceptronClassifier;

/**
 * Program for testing the PerceptronClassifier.
 */
public class PerceptronClassifierTest extends JPanel{
	static Map<Integer, Double> m = new HashMap<>();
	/**
	 * Read the Data from the file with the given filename, create a
	 * PerceptronClassifier with the appropriate number of inputs for the data,
	 * and train it on the data, printing its accuracy after each step. 
	 */
	public static void test(String filename, int nsteps, double alpha) throws IOException {
		System.out.println("filename: " + filename);
		System.out.println("nsteps: " + nsteps);
		System.out.println("alpha: " + alpha);
		List<Example> examples = Data.readFromFile(filename);
		int ninputs = examples.get(0).inputs.length; 
		PerceptronClassifier classifier = new PerceptronClassifier(ninputs) {
			@Override
			public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
				double accuracy = accuracy(examples);
				System.out.println(stepnum + "\t" + accuracy);
				m.put(stepnum,accuracy);
				
			}
		};
		if (alpha > 0) {
			classifier.train(examples, nsteps, alpha);
		} else {
			classifier.train(examples, 100000, new DecayingLearningRateSchedule());
		}
	}
	
	/**
	 * Train a PerceptronClassifier on a file of examples and
	 * print its accuracy after each training step.
	 */
	public static void main(String[] argv) throws IOException {
		if (argv.length < 3) {
			System.out.println("usage: java PerceptronClassifierTest data-filename nsteps alpha");
			System.out.println("       specify alpha=0 to use decaying learning rate schedule");
			System.exit(-1);
		}
		//First argument is the file name
		String filename = argv[0];
		//Second argument is the number of steps
		int nsteps = Integer.parseInt(argv[1]);
		//Third argument is the learning rate
		//Enter 0 to use decaying learning rate
		double alpha = Double.parseDouble(argv[2]);
		
		PerceptronClassifierTest pcanvas = new PerceptronClassifierTest();
		String prefix = "src/learn/lc/examples/";
		test(prefix+filename,nsteps,alpha);
		JFrame f = new JFrame("Graph");
		f.add(pcanvas);
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

/*Project for Calculus BC Class 2019 - Abstract Art

Objective: Create a work of abstract art based off a mathematical pattern.

Description of the work: An interactive heat map of the occurrences of numbers produced by the 
sequence in the Collatz conjecture. User inputs the number of iterations to run(I) and the height and width
of the resulting grid(H and W), and the program will generate a grid of cells, with
each cell corresponding to an integer in the range specified by the user (1 to H*W), and the color of each cell
determined by the total number of times that number occurs when running the Collatz sequence using numbers in 
the user's range as inputs (1 to I).
Black ------------------ Bright Blue
Less Times ------------- More Times
*/

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings({ "serial", "unused" })
public class CollatzVisualizer extends Canvas{

	private static JFrame frame;
	private static JTextField heightField, widthField, iterationsField;
	private static CollatzVisualizer canvas;
	private static int[] heatMap;
	private static int height, width, iterations;
	
	public static void main(String[] args) throws InterruptedException {
		
		
		heightField = new JTextField();
		widthField = new JTextField();
		iterationsField = new JTextField();
		
		Object[] message = {
		    "Heigth:", heightField,
		    "Width:", widthField,
		    "Iterations:", iterationsField
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Input Parameters", JOptionPane.DEFAULT_OPTION);
		if(option == JOptionPane.OK_OPTION)
		{
			try 
			{
				height = Integer.parseInt(heightField.getText());
				width = Integer.parseInt(widthField.getText());
				iterations = Integer.parseInt(iterationsField.getText());
				heatMap = new int[height*width];
		    	frame = new JFrame(width + "x" + height + " | " + iterations + " iterations");
		    	canvas = new CollatzVisualizer();
		    	frame.add(canvas);
		    	canvas.setSize(1200, 1200);
		    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		    	frame.setVisible(true);
		    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				runProcess();
				frame.setTitle(width + "x" + height + " | " + iterations + " iterations - Process Finished");
			}
			catch(NumberFormatException e)
			{
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Please only input positive integers");
				main(null);
			}
		}

      
	}
	
	public static void runProcess() throws InterruptedException
	{
    	for(int i = 1; i <= iterations; i++)
    	{
    		runSequence(i, heatMap);
    		//System.out.println(Arrays.toString(heatMap));
    		canvas.paint(canvas.getGraphics());
    		if(iterations<200)
    			Thread.sleep(200);
    		else if(iterations<400)
    			Thread.sleep(100);
    		else
    			Thread.sleep(50);
    	}
	}
	
	public void paint(Graphics g)
	{
		int h = canvas.getHeight() / height;
		int w = canvas.getWidth() / width;
		int count = 0;
		for(int col = 0; col < height; col++){
		    for(int row = 0; row < width; row++){
		    	g.setColor(new Color(0, (float)heatMap[count]/iterations/2, (float)heatMap[count]/iterations));
		        g.fillRect(row * w, col * h, w, h);
		        //g.drawString(""+heatMap[count++], row*width, (col+ 1)*height);
		        count++;
		    }
		}
	}
	
	public static void runSequence(int num, int[] map)
	{
		if(num <= map.length)
			map[num - 1]++;
		if(num == 1)
			return;
		if(num % 2 == 1)
			runSequence(num * 3 + 1, map);
		else
			runSequence(num / 2, map);
	}

}

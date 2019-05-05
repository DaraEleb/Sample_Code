package com.sketchpad.gui;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JFrame;

import processing.core.PApplet;

/**
 * 
 * @author jairus-main
 *
 */
public class SketchpadApplet extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean drawing = false;
	private final int BLACK = 0;
	private final int WHITE = 255;
	
	private Queue<SketchLine> queue;
	
	public void setup() {
		size(ConfigGUI.WIDTH, ConfigGUI.HEIGHT);
		background(BLACK);
		queue = new ConcurrentLinkedQueue<SketchLine>();
	}
	
	

	/** 
	 * drawing makes a point on the sketchpad
	 * @param p
	 */
	synchronized public void update(Object o){
		if(o instanceof SketchLine){
			SketchLine line = (SketchLine)o;
			line(line.x, line.y, line.mx, line.my);
		}
	}
	
	synchronized public void update(float newX, float newY, float newMx, float newMy){
			line(newX, newY, newMx, newMy);
			queue.add(new SketchLine(newX, newY, newMx, newMy));
	}

	
	/**
	 * returns the line of what was drawn on the canvas
	 * @return
	 */
	synchronized public SketchLine getLine(){
		if(drawing){
			return queue.poll();
		}
		return null;
	}
	
	/**
	 * updates the line variable and the canvas
	 */
	public void draw() {
		stroke(WHITE);
		if (mousePressed) {
			drawing = true;
			
			//line(mouseX, mouseY, pmouseX, pmouseY);
			update(mouseX, mouseY, pmouseX, pmouseY);
			//point(mouseX,mouseY);
			//updateSkectchLine(mouseX, mouseY, pmouseX, pmouseY);
		}
		drawing = false;
	}

	public static void main(String[] args) {
		// PApplet.main(new String[] { "--present", "MyProcessingSketch" });
		JFrame f = new JFrame("PASS");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(ConfigGUI.WIDTH, ConfigGUI.HEIGHT);
		// f.add(new Sample());
		SketchpadApplet app = new SketchpadApplet();
		app.init();
		f.setLocationRelativeTo(null);
		f.getContentPane().add(app);
		f.pack();
		f.setVisible(true);

	}

}

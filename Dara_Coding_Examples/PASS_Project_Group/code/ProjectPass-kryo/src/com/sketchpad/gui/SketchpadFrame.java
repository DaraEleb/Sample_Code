package com.sketchpad.gui;

import javax.swing.JFrame;
/**
 * 
 * @author jairus-main
 *
 */
public class SketchpadFrame {

	private JFrame frame;
	private SketchpadApplet pad;

	public JFrame getFrame() {
		return frame;
	}

	public SketchpadApplet getPad() {
		return pad;
	}

	public SketchpadFrame() {
		frame = new JFrame(ConfigGUI.FRAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(ConfigGUI.WIDTH, ConfigGUI.HEIGHT);
		frame.setResizable(false);
		
		// create the sketchpad applet here
		pad = new SketchpadApplet();
		pad.init();
		
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(pad);
		frame.pack();
		frame.setVisible(true);
	}
	
	public SketchpadFrame(String frameTitle) {
		frame = new JFrame(ConfigGUI.FRAME_TITLE + ": " + frameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(ConfigGUI.WIDTH, ConfigGUI.HEIGHT);
		frame.setResizable(false);
		
		// create the sketchpad applet here
		pad = new SketchpadApplet();
		pad.init();
		
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(pad);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void setVisible(boolean b){
		frame.setVisible(b);
	}
	
	public void close(){
		frame.setVisible(false);
		frame.dispose();
	}
	

	public static void main(String[] args) {
		new SketchpadFrame();
	}

	public void setFrameTitle(String string) {
		frame.setTitle(string);
		frame.validate();
		
	}

}

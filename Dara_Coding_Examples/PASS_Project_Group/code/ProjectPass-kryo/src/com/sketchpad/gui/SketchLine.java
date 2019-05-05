package com.sketchpad.gui;

import java.io.Serializable;

public class SketchLine implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	volatile public float x;
	volatile public float y;
	volatile public float mx;
	volatile public float my;
	public SketchLine(float x,float y,float mx,float my) {
		this.x = x;
		this.y = y;
		this.mx = mx;
		this.my = my;
	}
	public SketchLine(){
		this.x = 0;
		this.y = 0;
		this.mx = 0;
		this.my = 0;
	}
	/**
	 * copy constructor
	 * @param currLine
	 */
	public SketchLine(SketchLine currLine) {
		this.x = currLine.x;
		this.y = currLine.y;
		this.mx = currLine.mx;
		this.my = currLine.my;
	}
	
	public String toString(){
		return "x:" + x +" y:" + y+" mx: " + mx + " my:" + my;
	}

}

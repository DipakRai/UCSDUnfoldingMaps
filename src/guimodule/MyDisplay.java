package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3338667324873615366L;
	

	public void setUp(){
		size(400,400);
		background(255,0,0);
	}
	
	public void draw(){
		ellipse(50,100,75,75);
	}
}

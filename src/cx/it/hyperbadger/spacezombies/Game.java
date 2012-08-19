package cx.it.hyperbadger.spacezombies;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
@SuppressWarnings("unused")
public class Game {
	private int fps, lastFPS;
	public Game(){
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setTitle("Space Zombies");
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Could not create display");
			System.exit(0);
		}
		//start opengl
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 600, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		//initialize loop
		while(!Display.isCloseRequested()){
			loop();
		}
		//loop end destroy display
		Display.destroy();
	}
	public void loop(){
		//clear
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		//draw
		
		//draw quad 
		glBegin(GL_QUADS);
	    	glVertex2f(100,100); //topleft
	    	glVertex2f(100+200,100); //top right
	    	glVertex2f(100+200,100+200); //bottom right
	    	glVertex2f(100,100+200); //bottom left
	    glEnd();
	    
		//update
		Display.update();
		Display.sync(60);
	}
}

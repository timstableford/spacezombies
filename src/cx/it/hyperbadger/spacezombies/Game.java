package cx.it.hyperbadger.spacezombies;

import java.io.IOException;
import java.math.BigDecimal;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import cx.it.hyperbadger.spacezombies.explorer.Planet;

import static org.lwjgl.opengl.GL11.*;
@SuppressWarnings("unused")
public class Game {
	private int fps, lastFPS;
	private Planet planetEarth, theSun, theMoon;
	public static final int G = 7;
	private static long time, lastFrame;
	public Game(){
		try {
			Display.setDisplayMode(new DisplayMode(1600,1000));
			Display.setTitle("Space Zombies");
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Could not create display");
			System.exit(0);
		}
		//start opengl
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 1600, 1000, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		//load planets
		theSun = new Planet(800,500,100,10000,"Sun","sun.png",null);
		planetEarth = new Planet(1000,500,20,10,"Earth","earth.png",theSun);
		theMoon = new Planet(1020,500,5,1,"Moon","moon.png",planetEarth);
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
		theSun.draw();
		planetEarth.draw();
		theMoon.draw();
	    //planetEarth.move();
	    //theMoon.move();
		//update
		Display.update();
		Display.sync(60);
	}
	public static int getDelta() {
	    long time = Sys.getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    return delta;
	}
}

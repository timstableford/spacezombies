package cx.it.hyperbadger.spacezombies;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import cx.it.hyperbadger.spacezombies.explorer.Planet;
import cx.it.hyperbadger.spacezombies.explorer.Ship;
import cx.it.hyperbadger.spacezombies.explorer.SolarSystem;

import static org.lwjgl.opengl.GL11.*;
@SuppressWarnings("unused")
public class Game {
	private int fps, lastFPS;
	private Planet planetEarth, theSun, theMoon;
	public static final int G = 7;
	private static long time, lastFrame;
	private SolarSystem sol = null;
	private Ship ship = null;
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
		glShadeModel(GL11.GL_SMOOTH);                            // Enable Smooth Shading
        glClearColor(0.0f, 0.0f, 0.0f, 0.5f);               // Black Background
        glClearDepth(1.0f);
		//load planets
		ArrayList<Planet> solPlanets = new ArrayList<Planet>();
		sol = new SolarSystem("Sole",solPlanets);
		solPlanets.add(new Planet(800,500,100,10000,"Sun","sun.png",null));
		solPlanets.add(new Planet(1000,500,20,10,"Earth","earth.png",sol.findPlanet("Sun")));
		solPlanets.add(new Planet(1025,500,5,1,"Moon","moon.png",sol.findPlanet("Earth")));
		ship = new Ship(600,600,200,"spaceship.png");
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
		sol.move();
		sol.draw();
		ship.draw();
		ship.move(sol.getMasses());
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

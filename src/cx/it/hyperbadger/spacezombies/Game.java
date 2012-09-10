package cx.it.hyperbadger.spacezombies;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import cx.it.hyperbadger.spacezombies.explorer.Mass;
import cx.it.hyperbadger.spacezombies.explorer.Planet;
import cx.it.hyperbadger.spacezombies.explorer.Ship;
import cx.it.hyperbadger.spacezombies.explorer.ShipControl;
import cx.it.hyperbadger.spacezombies.explorer.SolarSystem;
import cx.it.hyperbadger.spacezombies.explorer.Sun;

import static org.lwjgl.opengl.GL11.*;
@SuppressWarnings("unused")
public class Game {
	private int fps, lastFPS;
	private Planet planetEarth, theSun, theMoon;
	public static final double G = 100;
	private static long time, lastFrame;
	private SolarSystem sol = null;
	private Ship ship = null;
	public static long delta = 160;
	private ShipControl shipControl = null;
	private int screenWidth = 1000, screenHeight = 700;
	public static void main(String[] args){
		new Game();
	}
	public Game(){
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth,screenHeight));
			Display.setTitle("Space Zombies");
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Could not create display");
			System.exit(0);
		}
		//start opengl
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
        //clear background
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);               // Black Background
        glClearDepth(1.0f);
        //png transparency fix
        glEnable(GL_BLEND); 
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        //antistropic filtering
        glTexParameteri(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 8);
		//load planets
		ArrayList<Mass> solMass = new ArrayList<Mass>();
		sol = new SolarSystem("Sole",solMass);
		solMass.add(new Sun(10000,0,0,"Sun","sun.png",50));
		solMass.add(new Planet(200,0,20,10,"Earth","earth.png",sol.findMass("Sun")));
		solMass.add(new Planet(20,0,6,1,"Moon","moon.png",sol.findMass("Earth")));
		ship = new Ship(200,200,100,"spaceship.png");
		shipControl = new ShipControl(ship);
		//initialize loop
		Display.update();
		Display.sync(60);
		Game.delta = getDelta();
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
		shipControl.update();
		ship.move(sol.getMasses());
		//translate
		double x = ship.getX()-Display.getWidth()/2;
		double y = ship.getY()-Display.getHeight()/2;
		GL11.glTranslatef(-(float)x, -(float)y, 0);
		//draw
		sol.draw();
		//detrsnalate
		GL11.glTranslatef((float)x, (float)y, 0);
		ship.draw();
		//update
		Display.update();
		Display.sync(60);
		Display.setTitle("Space Zombies - "+ship.getVelocity()+"m/s");
		Game.delta = getDelta();
	}
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
}

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

import cx.it.hyperbadger.spacezombies.explorer.ExplorerGUI;
import cx.it.hyperbadger.spacezombies.explorer.Mass;
import cx.it.hyperbadger.spacezombies.explorer.Planet;
import cx.it.hyperbadger.spacezombies.explorer.Ship;
import cx.it.hyperbadger.spacezombies.explorer.ShipControl;
import cx.it.hyperbadger.spacezombies.explorer.SolarSystem;
import cx.it.hyperbadger.spacezombies.explorer.Sun;
import cx.it.hyperbadger.spacezombies.gui.GUI;

import static org.lwjgl.opengl.GL11.*;
@SuppressWarnings("unused")
public class Game {
	private int fps, lastFPS;
	private Planet planetEarth, theSun, theMoon;
	public static final BigDecimal G = new BigDecimal(6.673*Math.pow(10,-11));
	public static final BigDecimal C = new BigDecimal("299792458");//299792458;
	private static long time, lastFrame;
	private SolarSystem sol = null;
	private Ship ship = null;
	public static long delta = 160;
	private ShipControl shipControl = null;
	private int screenWidth = 1000, screenHeight = 700;
	private GUI gui = null;
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
		sol = new SolarSystem("Sole");
		sol.addMass(new Sun(new BigDecimal(1.9891*Math.pow(10, 30)),
				new BigDecimal(0),new BigDecimal(0),"Sun","sun.png",
				new BigDecimal(695500000)));
		
		sol.addMass(new Planet(new BigDecimal(149597887500.0)
				,new BigDecimal(0),
				new BigDecimal(6378100),
				new BigDecimal(5.97219*Math.pow(10, 24)),
				"Earth","earth.png",sol.findMass("Sun")));
		
		sol.addMass(new Planet(new BigDecimal(385000000),
				new BigDecimal(0),
				new BigDecimal(1737100),
				new BigDecimal(7.34767309*Math.pow(10, 22)),
				"Moon","moon.png",sol.findMass("Earth")));
		
		ship = new Ship(new BigDecimal(10000),
				new BigDecimal(2.512*Math.pow(10, 10)),
				new BigDecimal(1.4747*Math.pow(10,11)),
				"spaceship.png");
		
		shipControl = new ShipControl(ship);
		//start gui
		gui = new ExplorerGUI(ship, sol);
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
		if((gui!=null&&!gui.mouseInGUI())||gui==null){
			shipControl.update();
		}
		ship.move(sol.getMasses());
		//translate
		float x = ship.getX().multiply(gui.getScale()).floatValue()-Display.getWidth()/2;
		float y = ship.getY().multiply(gui.getScale()).floatValue()-Display.getHeight()/2;
		GL11.glTranslatef(-(float)x, -(float)y, 0);
		//draw
		//set ALL the scales
		sol.setScale(gui.getScale());
		ship.setScale(gui.getScale());
		sol.draw();
		//detrsnalate
		GL11.glTranslatef((float)x, (float)y, 0);
		ship.draw();
		//if gui draw
		if(gui!=null){
			gui.draw();
			gui.poll();
		}
		//update
		Display.update();
		Display.sync(60);
		Display.setTitle("Space Zombies - "+ship.getVelocity().intValue()/1000+"km/s");
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
	public static BigDecimal bigDelta(){
		BigDecimal a = new BigDecimal(Game.delta);
		BigDecimal b = new BigDecimal("1000");
		return a.divide(b);
	}
}

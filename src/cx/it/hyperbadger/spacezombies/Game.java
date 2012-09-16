package cx.it.hyperbadger.spacezombies;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import cx.it.hyperbadger.spacezombies.explorer.Collidable;
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
	public static final String title = "SpaceZombies";
	private Planet planetEarth, theSun, theMoon;
	public static final double G = 6.673*Math.pow(10,-11);
	public static final double C = 299792458;
	private static long time, lastFrame;
	private SolarSystem sol = null;
	private Ship ship = null;
	private Console console = null;
	public static long delta = 160;
	private ShipControl shipControl = null;
	private int screenWidth = 800, screenHeight = 600;
	private GUI gui = null;
	private ArrayList<String> commands = new ArrayList<String>();
	public static void main(String[] args){
		String pS = System.getProperty("file.separator");
		String os = System.getProperty("os.name").toLowerCase();
		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.home")+pS+Game.title+pS+"native");
		boolean fullscreen = false;
		int x = 800, y = 600;
		if(args.length>0){
			for(String s: args){
				if("fullscreen".equals(s)){
					fullscreen = true;
				}else if(s.contains(":")){
					String[] split = s.split(":");
					if(split.length>1){
						int f;
						try{
							f = Integer.parseInt(split[1]);
							if("x".equals(split[0])){
								x = f;
							}else if("y".equals(split[0])){
								y = f;
							}
						}catch(NumberFormatException e){}
					}
				}
			}
		}
		new Game(fullscreen,x,y);
	}
	public Game(boolean fullscreen, int screenWidth, int screenHeight){
		try {
			if(fullscreen){
				screenWidth = Display.getDesktopDisplayMode().getWidth();
				screenHeight = Display.getDesktopDisplayMode().getHeight();
			}
			this.setDisplayMode(screenWidth, screenHeight, fullscreen);
			Display.create();
			Display.setTitle("Space Zombies");
		} catch (LWJGLException e) {
			System.err.println("Could not set display mode");
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
		sol.addMass(new Sun(1.9891*Math.pow(10, 30),0,0,"Sun","sun.png",695500000));
		sol.addMass(new Planet(149597887500.0,0,6378100,5.97219*Math.pow(10, 24),"Earth","earth.png",sol.findMass("Sun")));
		sol.addMass(new Planet(385000000,0,1737100,7.34767309*Math.pow(10, 22),"Moon","moon.png",sol.findMass("Earth")));
		ship = new Ship(10000,sol.findMass("Earth").getX(),sol.findMass("Earth").getY(),"spaceship.png");
		ship.setAttracts(sol.getMasses());
		sol.addMass(ship);
		shipControl = new ShipControl(ship);
		//start gui
		gui = new ExplorerGUI(ship, sol, this);
		//initialize loop
		Display.update();
		Display.sync(60);
		Game.delta = getDelta();
		//initialize the console
		console = new Console(ship,sol,gui,this);
		Thread t = new Thread(console);
		t.start();
		while(!Display.isCloseRequested()){
			loop();
		}
		//loop end destroy display
		console.stop();
		Display.destroy();
		System.exit(0);
	}
	public void loop(){
		for(String s: commands){
			console.processLine(s);
		}
		commands = new ArrayList<String>();
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			System.exit(0);
		}
		//clear
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		//draw
		sol.move();
		if((gui!=null&&!gui.mouseInGUI())||gui==null){
			shipControl.update();
		}
		//translate
		double x = ship.getX()*gui.getScale()-Display.getWidth()/2;
		double y = ship.getY()*gui.getScale()-Display.getHeight()/2;
		GL11.glPushMatrix();
		GL11.glTranslated(-x, -y, 0);
		GL11.glPushMatrix();
		GL11.glScaled(gui.getScale(), gui.getScale(), gui.getScale());
		sol.draw();
		//de-translate
		//GL11.glTranslated(x, y, 0);
		//if gui draw
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		ship.draw();
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		if(gui!=null){
			gui.draw();
			gui.poll();
		}
		GL11.glPopMatrix();
		//update
		Display.update();
		Display.sync(40);
		BigDecimal b = new BigDecimal(ship.getVelocity());
		BigDecimal c = new BigDecimal("1000");
		MathContext m = new MathContext(4);
		
		BigDecimal d = b.divide(c,m);
		Display.setTitle("Space Zombies - "+d+"km/s");
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
	public synchronized void addCommand(String s){
		commands.add(s);
	}
	public void setDisplayMode(int width, int height, boolean fullscreen) {

	    // return if requested DisplayMode is already set
	    if ((Display.getDisplayMode().getWidth() == width) && 
	        (Display.getDisplayMode().getHeight() == height) && 
		(Display.isFullscreen() == fullscreen)) {
		    return;
	    }

	    try {
	        DisplayMode targetDisplayMode = null;
			
		if (fullscreen) {
		    DisplayMode[] modes = Display.getAvailableDisplayModes();
		    int freq = 0;
					
		    for (int i=0;i<modes.length;i++) {
		        DisplayMode current = modes[i];
						
			if ((current.getWidth() == width) && (current.getHeight() == height)) {
			    if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
			        if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
				    targetDisplayMode = current;
				    freq = targetDisplayMode.getFrequency();
	                        }
	                    }

			    // if we've found a match for bpp and frequence against the 
			    // original display mode then it's probably best to go for this one
			    // since it's most likely compatible with the monitor
			    if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
	                        (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
	                            targetDisplayMode = current;
	                            break;
	                    }
	                }
	            }
	        } else {
	            targetDisplayMode = new DisplayMode(width,height);
	        }

	        if (targetDisplayMode == null) {
	            System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
	            return;
	        }

	        Display.setDisplayMode(targetDisplayMode);
	        Display.setFullscreen(fullscreen);
				
	    } catch (LWJGLException e) {
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}
}

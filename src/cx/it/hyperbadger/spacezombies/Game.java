package cx.it.hyperbadger.spacezombies;

import java.io.IOException;

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
	private Texture earth = null;
	private Planet planetEarth;
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
		glEnable(GL_TEXTURE_2D);
		//load planets
		planetEarth = new Planet(50,10,"Earth",getTexture("earth.png"));
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
		planetEarth.draw();
	    
		//update
		Display.update();
		Display.sync(60);
	}
	public Texture getTexture(String name){
			Texture t = null;
			try {
				t = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+name));
			} catch (IOException e) {
				System.err.println("Could not load texture: "+name);
			}
			return t;
	}
}

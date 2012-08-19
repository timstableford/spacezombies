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

import static org.lwjgl.opengl.GL11.*;
@SuppressWarnings("unused")
public class Game {
	private int fps, lastFPS;
	private Texture wood = null;
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
		//load textures
		wood = getTexture("wood.png");
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
			wood.bind();
			glTexCoord2f(0,0);
	    	glVertex2f(100,100); //topleft
	    	glTexCoord2f(1,0);
	    	glVertex2f(100+200,100); //top right
	    	glTexCoord2f(1,1);
	    	glVertex2f(100+200,100+200); //bottom right
	    	glTexCoord2f(0,1);
	    	glVertex2f(100,100+200); //bottom left
	    glEnd();
	    
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

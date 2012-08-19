package cx.it.hyperbadger.spacezombies.explorer;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;
public class Planet {
	private int radius, mass;
	private String name;
	private Texture texture;
	private int x,y;
	public Planet(int x, int y, int radius, int mass, String name, String texture){
		this.radius = radius;
		this.mass = mass;
		this.name = name;
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+texture));
		} catch (IOException e) {
			System.err.println("Could not load texture: "+texture);
		}
		this.x = x;
		this.y = y;
	}
	public void draw(){
		glBegin(GL_QUADS);
		texture.bind();
		glTexCoord2f(0,0);
    	glVertex2f(x-radius,y-radius); //topleft
    	glTexCoord2f(1,0);
    	glVertex2f(x+radius,y-radius); //top right
    	glTexCoord2f(1,1);
    	glVertex2f(x+radius,y+radius); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2f(x-radius,y+radius); //bottom left
    	glEnd();
	}
}

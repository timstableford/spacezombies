package cx.it.hyperbadger.spacezombies.explorer;

import org.newdawn.slick.opengl.Texture;
import static org.lwjgl.opengl.GL11.*;
public class Planet {
	private int radius, mass;
	private String name;
	private Texture texture;
	public Planet(int radius, int mass, String name, Texture texture){
		this.radius = radius;
		this.mass = mass;
		this.name = name;
		this.texture = texture;
	}
	public void draw(){
		glBegin(GL_QUADS);
		texture.bind();
		glTexCoord2f(0,0);
    	glVertex2f(100,100); //topleft
    	glTexCoord2f(1,0);
    	glVertex2f(100+200,100); //top right
    	glTexCoord2f(1,1);
    	glVertex2f(100+200,100+200); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2f(100,100+200); //bottom left
    glEnd();
	}
}

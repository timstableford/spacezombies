package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Ship extends Mass implements Drawable{
	private Texture texture = null;
	private float rotation = 0;
	private Vector2f velocity = new Vector2f();
	public Ship(int mass, int x, int y, String texture){
		super(mass,x,y);
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+texture));
		} catch (IOException e) {
			System.err.println("Could not load texture: "+texture);
		}
	}
	@Override
	public void draw() {
		texture.bind();
		//int h = texture.getImageHeight();
		//int w = texture.getImageWidth();
		int h = 20;
		int w = 20;
		GL11.glTranslatef((float)x, (float)y, 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-(float)x, -(float)y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
    	glVertex2f((int)x-w,(int)y-h); //topleft
    	glTexCoord2f(1,0);
    	glVertex2f((int)x+w,(int)y-h); //top right
    	glTexCoord2f(1,1);
    	glVertex2f((int)x+w,(int)y+h); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2f((int)x-w,(int)y+h); //bottom left
    	glEnd();
    	GL11.glTranslatef((float)x, (float)y, 0);
    	GL11.glRotatef(-rotation, 0f, 0f, 1f);
    	GL11.glTranslatef(-(float)x, -(float)y, 0);
	}
	public void move(ArrayList<Mass> attracts){
		rotation++;
		velocity
	}
}

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

import cx.it.hyperbadger.spacezombies.Game;

public class Ship extends Mass implements Drawable{
	private Texture texture = null;
	private float rotation = 0;
	private Vector2f velocity = new Vector2f();
	public Ship(int mass, int x, int y, String texture){
		super(mass,x,y,"Ship");
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
		//GL11.glTranslatef((float)x, (float)y, 0);
		//GL11.glRotatef(rotation, 0f, 0f, 1f);
		//GL11.glTranslatef(-(float)x, -(float)y, 0);
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
    	//GL11.glTranslatef((float)x, (float)y, 0);
    	//GL11.glRotatef(-rotation, 0f, 0f, 1f);
    	//GL11.glTranslatef(-(float)x, -(float)y, 0);
	}
	public void move(ArrayList<Mass> attracts){
		//rotation++;
		
		Vector2f acceleration = new Vector2f();
		for(Mass m: attracts){
			Vector2f.add(acceleration, getVectorAcceleration(this,m), acceleration);
		}
		//update velocity
		//Vector2f.add(velocity, acceleration, velocity);
		velocity.x = velocity.x + acceleration.x*(float)0.16;
		velocity.y = velocity.y + acceleration.y*(float)0.16;
		//find displacement
		//double sX = 0.5*acceleration.x*Game.getDelta()*Game.getDelta();
		//double sY = 0.5*acceleration.y*Game.getDelta()*Game.getDelta();
		//update coordinates
		this.x = this.x + velocity.x*0.16;
		this.y = this.y + velocity.y*0.16;
		System.out.println(acceleration);
	}
	private Vector2f getVectorAcceleration(Mass from, Mass to){
		//from ship
		//to planet
		Vector2f ship = new Vector2f();
		ship.x = (float)this.getX();
		ship.y = (float)this.getY();
		Vector2f mass = new Vector2f();
		mass.x = (float)to.getX();
		mass.y = (float)to.getY();
		Vector2f force = new Vector2f();
		Vector2f.sub(mass, ship, force);
		Vector2f unitVector = new Vector2f();
		float divisor = (float) Math.sqrt(Math.pow(force.x, 2)+Math.pow(force.y, 2));
		unitVector.set(force.x/divisor,force.y/divisor);
		float magnitude = (float) this.getAccelerationMagnitude(to);
		Vector2f magVec = new Vector2f();
		magVec.set(unitVector.x*magnitude,unitVector.y*magnitude);
		return magVec;
	}
}

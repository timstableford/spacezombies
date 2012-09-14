package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cx.it.hyperbadger.spacezombies.Game;
import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.Vector2d;

public class Ship extends Mass implements Drawable, Collidable{
	private TextureName textureName = null;
	private float rotation = 0;
	private boolean first = true;
	private int h,w;
	private double force = 1000000000000.0;
	private Vector2d velocity = new Vector2d();
	private boolean auto = false;
	private long autoVelocity = 50;
	public Ship(int mass, double x, double y, String textureName){
		super(mass,x,y,"Ship");
		this.textureName = new TextureName(textureName);
		this.textureName.loadTexture();
		h = this.textureName.getTexture().getImageHeight()/20;
		w = this.textureName.getTexture().getImageWidth()/20;
		velocity.set(0,0);
	}
	@Override
	public void draw() {
		textureName.getTexture().bind();
		calculateAngle();
		if(this.scale<0.6){
			this.scale = 0.6;
		}
		GL11.glTranslatef((float)Display.getWidth()/2, (float)Display.getHeight()/2, 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-(float)Display.getWidth()/2, -(float)Display.getHeight()/2, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
    	glVertex2f((int)(Display.getWidth()/2-w*scale),(int)(Display.getHeight()/2-h*scale)); //topleft
    	glTexCoord2f(1,0);
    	glVertex2f((int)(Display.getWidth()/2+w*scale),(int)(Display.getHeight()/2-h*scale)); //top right
    	glTexCoord2f(1,1);
    	glVertex2f((int)(Display.getWidth()/2+w*scale),(int)(Display.getHeight()/2+h*scale)); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2f((int)(Display.getWidth()/2-w*scale),(int)(Display.getHeight()/2+h*scale)); //bottom left
    	glEnd();
    	GL11.glTranslatef((float)Display.getWidth()/2, (float)Display.getHeight()/2, 0);
		GL11.glRotatef(-rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-(float)Display.getWidth()/2, -(float)Display.getHeight()/2, 0);
	}
	public void applyForce(Vector2d force){
		calculateRelativity();
		velocity.x = velocity.x + (force.x*Game.delta/1000)/this.mass;
		velocity.y = velocity.y + (force.y*Game.delta/1000)/this.mass;
		calculateRelativity();
	}

	public void processCollisions(ArrayList<Collidable> toCheck){
		for(Collidable c: toCheck){
			if(c.collisionCheck(this)){
				this.collision(c);
				c.collision(this);
			}
		}
	}
	public void move(ArrayList<Mass> attracts){
		Vector2d planetForce = new Vector2d(0,0);
		for(Mass m: attracts){
			Vector2d.add(planetForce, getVectorForce(this,m), planetForce);
		}
		//we dont apply force initially, shit fucks up
		if(!first){
			this.applyForce(planetForce);
		}
		first = false;
		if(auto){
			Vector2d a = new Vector2d(0,0);
			double speed = velocity.length();
			double over = Math.pow(autoVelocity - speed, 2);
			a = velocity.unitVector();
			a.scale(over);
			applyForce(a);
		}
		//
		if(velocity.length()>(2.997*Math.pow(10,8))){
			velocity = velocity.unitVector();
			velocity = velocity.scale(2.997*Math.pow(10,8));
		}
		this.x = this.x + velocity.x*Game.delta/1000;
		this.y = this.y + velocity.y*Game.delta/1000;
	}
	private void calculateRelativity(){
		//calculate relativity
		mass = originalMass/Math.sqrt(1-Math.pow(velocity.length()/Game.C,2));
	}
	private Vector2d getVectorForce(Mass from, Mass to){
		//from ship
		//to planet
		Vector2d ship = new Vector2d();
		ship.x = (float)this.getX();
		ship.y = (float)this.getY();
		Vector2d mass = new Vector2d();
		mass.x = (float)to.getX();
		mass.y = (float)to.getY();
		Vector2d force = new Vector2d();
		Vector2d.sub(mass, ship, force);
		Vector2d unitVector = new Vector2d();
		float divisor = (float) Math.sqrt(Math.pow(force.x, 2)+Math.pow(force.y, 2));
		unitVector.set(force.x/divisor,force.y/divisor);
		float magnitude = (float) this.getForceMagnitude(to);
		Vector2d magVec = new Vector2d();
		magVec.set(unitVector.x*magnitude,unitVector.y*magnitude);
		return magVec;
	}
	private void calculateAngle(){
		Vector2d velocityUnit = new Vector2d();
		float velocityMag = (float) Math.sqrt(Math.pow(velocity.x, 2)+Math.pow(velocity.y, 2));
		if(velocityMag!=0){
			velocityUnit.set(velocity.x/velocityMag,velocity.y/velocityMag);
			rotation = (float)velocityUnit.getDegrees()+180;
		}
	}
	public double getVelocity(){
		return velocity.length();
	}
	@Override
	public void setTexture(TextureName t) {
		textureName = t;
	}
	@Override
	public TextureName getTexture() {
		return textureName;
	}
	public double getForce() {
		return force;
	}
	public void setForce(long force) {
		this.force = force;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public long getAutoVelocity() {
		return autoVelocity;
	}
	public void setAutoVelocity(long autoVelocity) {
		this.autoVelocity = autoVelocity;
	}
	@Override
	public double getCollisionRadius() {
		return (h+w)/4;
	}
	@Override
	public void collision(Collidable object) {
		System.out.println(this.getName()+" has collided with "+object.getName());
	}
}

package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cx.it.hyperbadger.spacezombies.Game;
import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.Vector2BD;

public class Ship extends Mass implements Drawable{
	private TextureName textureName = null;
	private BigDecimal rotation = new BigDecimal(0);
	private boolean first = true;
	private int h,w;
	private BigDecimal force = new BigDecimal("100000000000.0");
	private Vector2BD velocity = new Vector2BD();
	private boolean auto = false;
	private BigDecimal autoVelocity = new BigDecimal(50);
	public Ship(BigDecimal mass, BigDecimal x, BigDecimal y, String textureName){
		super(mass,x,y,"Ship");
		this.textureName = new TextureName(textureName);
		this.textureName.loadTexture();
		h = this.textureName.getTexture().getImageHeight()/20;
		w = this.textureName.getTexture().getImageWidth()/20;
		velocity.set(new BigDecimal(0),new BigDecimal(0));
	}
	@Override
	public void draw() {
		textureName.getTexture().bind();
		calculateAngle();
		if(this.scale.compareTo(new BigDecimal("0.6"))<0){
			this.scale = new BigDecimal("0.6");
		}
		GL11.glTranslatef((float)Display.getWidth()/2, (float)Display.getHeight()/2, 0);
		GL11.glRotatef(rotation.floatValue(), 0f, 0f, 1f);
		GL11.glTranslatef(-(float)Display.getWidth()/2, -(float)Display.getHeight()/2, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
    	glVertex2f((int)(Display.getWidth()/2-w*scale.floatValue()),(int)(Display.getHeight()/2-h*scale.floatValue())); //topleft
    	glTexCoord2f(1,0);
    	glVertex2f((int)(Display.getWidth()/2+w*scale.floatValue()),(int)(Display.getHeight()/2-h*scale.floatValue())); //top right
    	glTexCoord2f(1,1);
    	glVertex2f((int)(Display.getWidth()/2+w*scale.floatValue()),(int)(Display.getHeight()/2+h*scale.floatValue())); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2f((int)(Display.getWidth()/2-w*scale.floatValue()),(int)(Display.getHeight()/2+h*scale.floatValue())); //bottom left
    	glEnd();
    	GL11.glTranslatef((float)Display.getWidth()/2, (float)Display.getHeight()/2, 0);
		GL11.glRotatef(-rotation.floatValue(), 0f, 0f, 1f);
		GL11.glTranslatef(-(float)Display.getWidth()/2, -(float)Display.getHeight()/2, 0);
	}
	public void applyForce(Vector2BD force){
		calculateRelativity();
		velocity.x = velocity.x.add(
				(force.x.multiply((Game.bigDelta()).divide(this.mass,Vector2BD.mathContext))));
		
		velocity.y = velocity.y.add(
				(force.y.multiply((Game.bigDelta()).divide(this.mass,Vector2BD.mathContext))));
		calculateRelativity();
	}

	public void move(ArrayList<Mass> attracts){
		Vector2BD planetForce = new Vector2BD(new BigDecimal(0),new BigDecimal(0));
		for(Mass m: attracts){
			Vector2BD.add(planetForce, getVectorForce(this,m), planetForce);
		}
		//we dont apply force initially, shit fucks up
		if(!first){
			this.applyForce(planetForce);
		}
		first = false;
		if(auto){
			Vector2BD a = new Vector2BD(new BigDecimal(0),new BigDecimal(0));
			BigDecimal speed = velocity.length();
			BigDecimal over = (autoVelocity.subtract(speed)).pow(2);//Math.pow(autoVelocity - speed, 2);
			a = velocity.unitVector();
			a.scale(over);
			applyForce(a);
		}
		//
		BigDecimal maxVelocity = new BigDecimal(2.997*Math.pow(10,8));
		if(velocity.length().compareTo(maxVelocity)>0){
			velocity = velocity.unitVector();
			velocity = velocity.scale(maxVelocity);
		}
		this.x = this.x.add(velocity.x.multiply(Game.bigDelta()));
		this.y = this.y.add(velocity.y.multiply(Game.bigDelta()));
	}
	private void calculateRelativity(){
		//calculate relativity
		BigDecimal a = new BigDecimal("1").subtract((velocity.length().divide(Game.C,Vector2BD.mathContext)).pow(2));
		if(a.compareTo(new BigDecimal("0"))<0){
			a = new BigDecimal("0");
		}
		//mass = originalMass.divide(Vector2BD.sqrt(a, Vector2BD.scale),Vector2BD.mathContext);
	}
	private Vector2BD getVectorForce(Mass from, Mass to){
		//from ship
		//to planet
		Vector2BD ship = new Vector2BD();
		ship.x = this.getX();
		ship.y = this.getY();
		Vector2BD mass = new Vector2BD();
		mass.x = to.getX();
		mass.y = to.getY();
		Vector2BD force = new Vector2BD();
		Vector2BD.sub(mass, ship, force);
		Vector2BD unitVector = new Vector2BD();
		unitVector = force.unitVector();
		BigDecimal magnitude = this.getForceMagnitude(to);
		Vector2BD magVec = new Vector2BD();
		magVec = unitVector.scale(magnitude);
		return magVec;
	}
	private void calculateAngle(){
		Vector2BD velocityUnit = new Vector2BD();
		velocityUnit = velocity.unitVector();
		rotation = new BigDecimal(velocityUnit.getDegrees()+180);
	}
	public BigDecimal getVelocity(){
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
	public BigDecimal getForce() {
		return force;
	}
	public void setForce(BigDecimal force) {
		this.force = force;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public BigDecimal getAutoVelocity() {
		return autoVelocity;
	}
	public void setAutoVelocity(BigDecimal autoVelocity) {
		this.autoVelocity = autoVelocity;
	}
}

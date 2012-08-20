package cx.it.hyperbadger.spacezombies.explorer;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import cx.it.hyperbadger.spacezombies.Game;

import static org.lwjgl.opengl.GL11.*;
public class Planet {
	private int radius;
	private int mass;
	private String name;
	private Texture texture;
	private Planet parentObject = null;
	private double x,y;
	private ArrayList<Planet> planets;
	private double initialVelocity = 0;
	public Planet(int x, int y, int radius, int mass, String name, String texture, Planet parent){
		this.radius = radius;
		this.mass = mass;
		this.name = name;
		this.planets = new ArrayList<Planet>();
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+texture));
		} catch (IOException e) {
			System.err.println("Could not load texture: "+texture);
		}
		this.x = x;
		this.y = y;
		if(parent!=null){
			this.parentObject = parent;
			initialVelocity = Math.sqrt(Game.G*(parent.getMass()/(getMe().distance(parent.getMe()))));
			parent.addChild(this);
			System.out.println("Initial velocity of "+name+" set to "+initialVelocity);
		}
	}
	public Point2D getMe(){
		return new Point2D.Double(this.x,this.y);
	}
	public int getMass(){
		return mass;
	}
	public void addChild(Planet child){
		planets.add(child);
	}
	public void move(){
		double deltaY = this.getY()-parentObject.getY();
		double deltaX = this.getX()-parentObject.getX();
		double a = Math.atan2(deltaY, deltaX)+Math.PI;
		double change = initialVelocity/5;
		double dX = change*Math.sin(Math.PI-a);
		double dY = change*Math.cos(Math.PI-a);
		move(dX,dY);
		for(Planet p: planets){
			p.move(dX,dY);
		}
	}
	public void move(double dX, double dY){
		double newX = dX+this.getX();
		double newY = dY+this.getY();
		this.x = newX;
		this.y = newY;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public void draw(){
		texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
    	glVertex2f((int)x-radius,(int)y-radius); //topleft
    	glTexCoord2f(1,0);
    	glVertex2f((int)x+radius,(int)y-radius); //top right
    	glTexCoord2f(1,1);
    	glVertex2f((int)x+radius,(int)y+radius); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2f((int)x-radius,(int)y+radius); //bottom left
    	glEnd();
	}
	public int getForce(Planet other){
		double distance = this.getMe().distance(other.getMe());
		System.out.println("rad = "+distance);
		distance = Math.pow(distance,2);
		int force = (int) (Game.G*this.mass*other.getMass()/distance);
		System.out.println(force);
		return force;
	}
}

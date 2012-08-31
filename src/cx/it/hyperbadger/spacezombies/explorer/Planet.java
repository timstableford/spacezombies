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
public class Planet extends Mass implements Drawable, Moveable{
	private int radius;
	private String name;
	private Texture texture;
	private Planet parentObject = null;
	private ArrayList<Planet> planets;
	private double initialVelocity = 0;
	private double initialDistance = 0;
	private double angle = 0;
	public Planet(int x, int y, int radius, int mass, String name, String texture, Planet parent){
		super(mass,x,y);
		this.radius = radius;
		this.name = name;
		this.planets = new ArrayList<Planet>();
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+texture));
		} catch (IOException e) {
			System.err.println("Could not load texture: "+texture);
		}
		if(parent!=null){
			this.parentObject = parent;
			initialVelocity = Math.sqrt(Game.G*(parent.getMass()/(this.distance(parent))));
			parent.addChild(this);
			System.out.println("Initial velocity of "+name+" set to "+initialVelocity);
			this.initialDistance = this.distance(parent);
		}
	}
	public String getName(){
		return name;
	}
	public void addChild(Planet child){
		planets.add(child);
	}
	public void move(){
		if(parentObject!=null){
			angle = angle + Math.pow(initialVelocity,0.1)/220;
			if(angle>Math.PI*2){
				angle = 0;
			}
			this.x = initialDistance*Math.sin(angle)+parentObject.getX();
			this.y = initialDistance*Math.cos(angle)+parentObject.getY();
		}
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
}

package cx.it.hyperbadger.spacezombies.explorer;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import cx.it.hyperbadger.spacezombies.Game;

import static org.lwjgl.opengl.GL11.*;
public class Planet extends Mass implements Drawable, Moveable{
	private int planetRadius;
	private Texture texture;
	private Mass parentMass = null;
	private ArrayList<Planet> planets;
	private double initialVelocity = 0;
	private double angle = 0;
	private float orbitRadius = 0;
	public Planet(float orbitRadius, double initialAngle, int planetRadius, int mass, String name, String texture, Mass parent){
		super(mass,(float) (orbitRadius*Math.sin(initialAngle)+parent.getX()),(float) (orbitRadius*Math.cos(initialAngle)+parent.getY()),name);
		angle = initialAngle;
		this.planetRadius = planetRadius;
		this.planets = new ArrayList<Planet>();
		this.orbitRadius = orbitRadius;
		//load texture
		try {
			this.texture = TextureLoader.getTexture("PNG", ClassLoader.class.getResourceAsStream("/cx/it/hyperbadger/spacezombies/res/"+texture));
		} catch (IOException e) {
			System.err.println("Could not load texture: "+texture);
		}
		if(parent instanceof Planet){
			Planet p = (Planet) parent;
			p.addChild(this);
		}
		this.parentMass = parent;
		initialVelocity = Math.sqrt(Game.G*(parent.getMass()/(orbitRadius)));
		System.out.println("Initial velocity of "+name+" set to "+initialVelocity);
	}
	public void addChild(Planet child){
		planets.add(child);
	}
	public void move(){
		angle = angle + Math.pow(initialVelocity,0.1)/220;
		if(angle>Math.PI*2){
			angle = 0;
		}
		this.x = (float) (orbitRadius*Math.sin(angle)+parentMass.getX());
		this.y = (float) (orbitRadius*Math.cos(angle)+parentMass.getY());
	}
	public void draw(){
		texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f((int)x-planetRadius,(int)y-planetRadius); //topleft
		glTexCoord2f(1,0);
		glVertex2f((int)x+planetRadius,(int)y-planetRadius); //top right
		glTexCoord2f(1,1);
		glVertex2f((int)x+planetRadius,(int)y+planetRadius); //bottom right
		glTexCoord2f(0,1);
    	glVertex2f((int)x-planetRadius,(int)y+planetRadius); //bottom left
    	glEnd();
	}
}

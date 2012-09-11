package cx.it.hyperbadger.spacezombies.explorer;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import cx.it.hyperbadger.spacezombies.Game;
import cx.it.hyperbadger.spacezombies.TextureName;

import static org.lwjgl.opengl.GL11.*;
public class Planet extends Mass implements Drawable, Moveable{
	private double planetRadius;
	private Mass parentMass = null;
	private ArrayList<Planet> planets;
	private double initialVelocity = 0;
	private double angle = 0;
	private double orbitRadius = 0;
	private TextureName textureName = null;
	public Planet(double orbitRadius, double initialAngle, double planetRadius, double mass, String name,String textureName, Mass parent){
		super(mass,(double) (orbitRadius*Math.sin(initialAngle)+parent.getX()),(double) (orbitRadius*Math.cos(initialAngle)+parent.getY()),name);
		angle = initialAngle;
		this.planetRadius = planetRadius;
		this.planets = new ArrayList<Planet>();
		this.orbitRadius = orbitRadius;
		//load texture
		this.textureName = new TextureName(textureName);
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
		this.x = (double) (orbitRadius*Math.sin(angle)+parentMass.getX());
		this.y = (double) (orbitRadius*Math.cos(angle)+parentMass.getY());
	}
	public void draw(){
		if(textureName.getTexture()!=null){
			textureName.getTexture().bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f((float)(x-planetRadius),(float)(y-planetRadius)); //topleft
			glTexCoord2f(1,0);
			glVertex2f((float)(x+planetRadius),(float)(y-planetRadius)); //top right
			glTexCoord2f(1,1);
			glVertex2f((float)(x+planetRadius),(float)(y+planetRadius)); //bottom right
			glTexCoord2f(0,1);
			glVertex2f((float)(x-planetRadius),(float)(y+planetRadius)); //bottom left
			glEnd();
		}
	}
	@Override
	public void setTexture(TextureName t) {
		textureName = t;
	}
	@Override
	public TextureName getTexture() {
		return textureName;
	}
}

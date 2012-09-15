package cx.it.hyperbadger.spacezombies.explorer;

import java.util.ArrayList;

import cx.it.hyperbadger.spacezombies.Game;
import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.Vector2d;

import static org.lwjgl.opengl.GL11.*;
public class Planet extends Mass implements Drawable, Moveable, Collidable{
	private double planetRadius;
	private Mass parentMass = null;
	private ArrayList<Planet> planets;
	private double initialVelocity = 0;
	private double angle = 0;
	private double orbitRadius = 0;
	private TextureName textureName = null;
	public Planet(double orbitRadius, double initialAngle, double planetRadius, double mass, String name,String textureName, Mass parent){
		super(mass,(orbitRadius*Math.sin(initialAngle)+parent.getX()),(orbitRadius*Math.cos(initialAngle)+parent.getY()),name);
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
	public boolean hasChildren(){
		if(planets!=null&&planets.size()>0){
			return true;
		}
		return false;
	}
	public Mass getParent(){
		return parentMass;
	}
	@Override
	public void move(){
		//angle = angle + Math.pow(initialVelocity,0.1)/220;
		angle = angle + (initialVelocity/orbitRadius)*(Game.delta/1000);
		if(angle>Math.PI*2){
			angle = 0;
		}
		this.x = (orbitRadius*Math.sin(angle)+parentMass.getX());
		this.y = (orbitRadius*Math.cos(angle)+parentMass.getY());
	}
	@Override
	public void draw(){
		if(textureName.getTexture()!=null){
			textureName.getTexture().bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2d((x*scale-planetRadius*scale),(y*scale-planetRadius*scale)); //topleft
			glTexCoord2f(1,0);
			glVertex2d((x*scale+planetRadius*scale),(y*scale-planetRadius*scale)); //top right
			glTexCoord2f(1,1);
			glVertex2d((x*scale+planetRadius*scale),(y*scale+planetRadius*scale)); //bottom right
			glTexCoord2f(0,1);
			glVertex2d((x*scale-planetRadius*scale),(y*scale+planetRadius*scale)); //bottom left
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
	@Override
	public Vector2d getLocation() {
		return new Vector2d(this.getX(),this.getY());
	}
	@Override
	public double getCollisionRadius() {
		return planetRadius;
	}
	@Override
	public void collision(Collidable object) {
		//ignore because generally we dont care if the planet is crashed into, only the ship
	}
}

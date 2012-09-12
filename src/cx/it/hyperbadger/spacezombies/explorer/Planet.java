package cx.it.hyperbadger.spacezombies.explorer;

import java.math.BigDecimal;
import java.util.ArrayList;

import cx.it.hyperbadger.spacezombies.Game;
import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.Vector2BD;

import static org.lwjgl.opengl.GL11.*;
public class Planet extends Mass implements Drawable, Moveable{
	private BigDecimal planetRadius;
	private Mass parentMass = null;
	private ArrayList<Planet> planets;
	private BigDecimal initialVelocity = new BigDecimal(0);
	private BigDecimal angle = new BigDecimal(0);
	private BigDecimal orbitRadius = new BigDecimal(0);
	private TextureName textureName = null;
	public Planet(BigDecimal orbitRadius, BigDecimal initialAngle, BigDecimal planetRadius, BigDecimal mass, String name,String textureName, Mass parent){
		super(mass,
				(orbitRadius.multiply(new BigDecimal(Math.sin(initialAngle.doubleValue())))).add(parent.getX()),
				(orbitRadius.multiply(new BigDecimal(Math.cos(initialAngle.doubleValue())))).add(parent.getY()),
				name);
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
		initialVelocity = Vector2BD.sqrt(Game.G.multiply(parent.getMass().divide(orbitRadius,Vector2BD.mathContext)),Vector2BD.scale);
		System.out.println("Initial velocity of "+name+" set to "+initialVelocity);
	}
	public void addChild(Planet child){
		planets.add(child);
	}
	public void move(){
		//angle = angle + Math.pow(initialVelocity,0.1)/220;
		angle = angle.add((initialVelocity.divide(orbitRadius,Vector2BD.mathContext)).multiply(new BigDecimal(Game.delta/1000)));
		if(angle.compareTo(new BigDecimal(Math.PI*2))>0){
			angle = new BigDecimal(0);
		}
		this.x = (orbitRadius.multiply(new BigDecimal(Math.sin(angle.doubleValue())))).add(parentMass.getX());
		this.y = (orbitRadius.multiply(new BigDecimal(Math.cos(angle.doubleValue())))).add(parentMass.getY());
	}
	public void draw(){
		if(textureName.getTexture()!=null){
			textureName.getTexture().bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f((x.floatValue()*scale.floatValue()-planetRadius.floatValue()*scale.floatValue()),
					(y.floatValue()*scale.floatValue()-planetRadius.floatValue()*scale.floatValue())); //topleft
			
			glTexCoord2f(1,0);
			glVertex2f((x.floatValue()*scale.floatValue()+planetRadius.floatValue()*scale.floatValue()),
					(y.floatValue()*scale.floatValue()-planetRadius.floatValue()*scale.floatValue())); //top right
			
			glTexCoord2f(1,1);
			glVertex2f((x.floatValue()*scale.floatValue()+planetRadius.floatValue()*scale.floatValue()),
					(y.floatValue()*scale.floatValue()+planetRadius.floatValue()*scale.floatValue())); //bottom right
			
			glTexCoord2f(0,1);
			glVertex2f((x.floatValue()*scale.floatValue()-planetRadius.floatValue()*scale.floatValue()),
					(y.floatValue()*scale.floatValue()+planetRadius.floatValue()*scale.floatValue())); //bottom left
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

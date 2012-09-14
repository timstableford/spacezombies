package cx.it.hyperbadger.spacezombies.explorer;

import cx.it.hyperbadger.spacezombies.Game;
import cx.it.hyperbadger.spacezombies.Vector2d;

public abstract class Mass implements Collidable{
	protected String name = "";
	protected double originalMass = 0;
	protected double mass = 0;
	protected double x = 0, y = 0;
	protected double scale = 1;
	public Mass(double mass, double x, double y, String name){
		this.originalMass = mass;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.name = name;
	}
	public double getForceMagnitude(Mass other){
		double distance = this.distance(other);
		distance = Math.pow(distance,2);
		if(distance<0.1){
			distance = 0.1;
		}
		double force = (Game.G*this.mass*other.getMass()/distance);
		return force;
	}
	public double getAccelerationMagnitude(Mass other){
		double distance = this.distance(other);
		distance = Math.pow(distance,2);
		double force = ((Game.G*other.getMass())/distance);
		return force;
	}
	public String getName(){
		return name;
	}
	public double getMass(){
		return mass;
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
	public void setScale(double scale){
		this.scale = scale;
	}
	public double getY(){
		return y;
	}
	public double distance(Mass p){
		return this.getLocation().distance(p.getLocation());
	}
	@Override
	public boolean collisionCheck(Collidable object) {
		if(this.getLocation().distance(object.getLocation())<(this.getCollisionRadius()+object.getCollisionRadius())){
			return true;
		}
		return false;
	}
	@Override
	public Vector2d getLocation() {
		return new Vector2d(this.getX(),this.getY());
	}
	public void setLocation(Vector2d d){
		this.setX(d.getX());
		this.setY(d.getY());
	}
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
}

package cx.it.hyperbadger.spacezombies.explorer;

import java.awt.geom.Point2D;

import cx.it.hyperbadger.spacezombies.Game;

public class Mass {
	protected int mass = 0;
	protected double x = 0, y = 0;
	public Mass(int mass, double x, double y){
		this.mass = mass;
		this.x = x;
		this.y = y;
	}
	public double getForce(Planet other){
		double distance = this.getMe().distance(other.getMe());
		System.out.println("rad = "+distance);
		distance = Math.pow(distance,2);
		double force = (Game.G*this.mass*other.getMass()/distance);
		System.out.println(force);
		return force;
	}
	public int getMass(){
		return mass;
	}
	public Point2D getMe(){
		return new Point2D.Double(this.x,this.y);
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
	public double distance(Planet p){
		Point2D a = new Point2D.Double(p.getX(),p.getY());
		Point2D b = new Point2D.Double(this.getX(),this.getY());
		return a.distance(b);
	}
}

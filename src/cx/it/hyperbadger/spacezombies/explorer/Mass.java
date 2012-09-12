package cx.it.hyperbadger.spacezombies.explorer;

import java.math.BigDecimal;

import cx.it.hyperbadger.spacezombies.Game;
import cx.it.hyperbadger.spacezombies.Vector2BD;

public class Mass {
	protected String name = "";
	protected BigDecimal originalMass = new BigDecimal(0);
	protected BigDecimal mass = new BigDecimal(0);
	protected BigDecimal x = new BigDecimal(0), y = new BigDecimal(0);
	protected BigDecimal scale = new BigDecimal(1);
	public Mass(BigDecimal mass, BigDecimal x, BigDecimal y, String name){
		this.originalMass = mass;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.name = name;
	}
	public BigDecimal getForceMagnitude(Mass other){
		BigDecimal distance = this.getMe().distance(other.getMe());
		distance = distance.pow(2);
		if(distance.compareTo(new BigDecimal("0.1"))>0){
			BigDecimal force = (Game.G.multiply(this.mass.multiply(other.getMass()))).divide(distance,Vector2BD.mathContext);
			return force;
		}
		return new BigDecimal(0);
	}
	public BigDecimal getAccelerationMagnitude(Mass other){
		BigDecimal force = this.getForceMagnitude(other).divide(this.mass);
		return force;
	}
	public String getName(){
		return name;
	}
	public BigDecimal getMass(){
		return mass;
	}
	public Vector2BD getMe(){
		return new Vector2BD(x,y);
	}
	public void move(BigDecimal dX, BigDecimal dY){
		BigDecimal newX = dX.add(this.getX());
		BigDecimal newY = dY.add(this.getY());
		this.x = newX;
		this.y = newY;
	}
	public BigDecimal getX(){
		return x;
	}
	public void setScale(BigDecimal scale){
		this.scale = scale;
	}
	public BigDecimal getY(){
		return y;
	}
}

package cx.it.hyperbadger.spacezombies.explorer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import cx.it.hyperbadger.spacezombies.Vector2d;

public class ShipControl {
	private Ship ship = null;
	public ShipControl(Ship ship){
		this.ship = ship;
	}
	public void update(){
		int wheel = Mouse.getEventDWheel();
		if(wheel!=0){
			double nForce = ship.getForce()+Math.sqrt(ship.getForce()*2)*wheel;
			ship.setForce(nForce);
		}
		boolean leftButtonDown = Mouse.isButtonDown(0);
		int x = Mouse.getX();
		int y = Mouse.getY();
		y = Display.getHeight()-y;
		Vector2d forceVec = new Vector2d();
		Vector2d shipVec = new Vector2d();
		shipVec.x = (float) Display.getWidth()/2;
		shipVec.y = (float) Display.getHeight()/2;
		Vector2d mouseVec = new Vector2d();
		mouseVec.x = x;
		mouseVec.y = y;
		Vector2d shipMouseVec = new Vector2d();
		Vector2d.sub(mouseVec, shipVec, shipMouseVec);
		float shipMouseMag = (float) Math.sqrt(Math.pow(shipMouseVec.x, 2)+Math.pow(shipMouseVec.y, 2));
		forceVec.x = shipMouseVec.x / shipMouseMag;
		forceVec.y = shipMouseVec.y / shipMouseMag;
		if(leftButtonDown){
			forceVec.x = ship.getForce()*forceVec.x;
			forceVec.y = ship.getForce()*forceVec.y;
			ship.applyForce(forceVec);
		}
		
	}
	public Ship getShip(){
		return ship;
	}
}

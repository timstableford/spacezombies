package cx.it.hyperbadger.spacezombies.explorer;

import java.math.BigDecimal;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import cx.it.hyperbadger.spacezombies.Vector2BD;

public class ShipControl {
	private Ship ship = null;
	public ShipControl(Ship ship){
		this.ship = ship;
	}
	public void update(){
		boolean leftButtonDown = Mouse.isButtonDown(0);
		int x = Mouse.getX();
		int y = Mouse.getY();
		y = Display.getHeight()-y;
		Vector2BD forceVec = new Vector2BD();
		Vector2BD shipVec = new Vector2BD();
		//shipVec.x = (float) ship.getX();
		//shipVec.y = (float) ship.getY();
		shipVec.x = new BigDecimal(Display.getWidth()/2);
		shipVec.y = new BigDecimal(Display.getHeight()/2);
		Vector2BD mouseVec = new Vector2BD();
		mouseVec.x = new BigDecimal(x);
		mouseVec.y = new BigDecimal(y);
		Vector2BD shipMouseVec = new Vector2BD();
		Vector2BD.sub(mouseVec, shipVec, shipMouseVec);
		forceVec = shipMouseVec.unitVector();
		if(leftButtonDown){
			forceVec = forceVec.scale(ship.getForce());
			ship.applyForce(forceVec);
		}

	}
}

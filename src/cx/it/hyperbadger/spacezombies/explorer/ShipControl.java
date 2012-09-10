package cx.it.hyperbadger.spacezombies.explorer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class ShipControl {
	private Ship ship = null;
	private int force = 10000;
	public ShipControl(Ship ship){
		this.ship = ship;
	}
	public void update(){
		boolean leftButtonDown = Mouse.isButtonDown(0);
		int x = Mouse.getX();
		int y = Mouse.getY();
		y = Display.getHeight()-y;
		Vector2f forceVec = new Vector2f();
		Vector2f shipVec = new Vector2f();
		//shipVec.x = (float) ship.getX();
		//shipVec.y = (float) ship.getY();
		shipVec.x = (float) Display.getWidth()/2;
		shipVec.y = (float) Display.getHeight()/2;
		Vector2f mouseVec = new Vector2f();
		mouseVec.x = x;
		mouseVec.y = y;
		Vector2f shipMouseVec = new Vector2f();
		Vector2f.sub(mouseVec, shipVec, shipMouseVec);
		float shipMouseMag = (float) Math.sqrt(Math.pow(shipMouseVec.x, 2)+Math.pow(shipMouseVec.y, 2));
		forceVec.x = shipMouseVec.x / shipMouseMag;
		forceVec.y = shipMouseVec.y / shipMouseMag;
		if(leftButtonDown){
			forceVec.x = force*forceVec.x;
			forceVec.y = force*forceVec.y;
			ship.applyForce(forceVec);
		}

	}
}

package cx.it.hyperbadger.spacezombies.explorer;

import cx.it.hyperbadger.spacezombies.Vector2d;

public interface Collidable {
	public Vector2d getLocation();
	public double getCollisionRadius();
	public void collision(Collidable object);
	public boolean collisionCheck(Collidable object);
	public String getName();
}

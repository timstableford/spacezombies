package cx.it.hyperbadger.spacezombies.explorer;

import org.newdawn.slick.opengl.Texture;

import cx.it.hyperbadger.spacezombies.TextureName;

public interface Drawable {
	public void draw();
	public void setTexture(TextureName t);
	public TextureName getTexture();
}

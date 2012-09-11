package cx.it.hyperbadger.spacezombies.explorer;

import cx.it.hyperbadger.spacezombies.TextureName;

public class Waypoint implements Drawable{
	private Mass destination = null;
	private TextureName textureName = null;
	public Waypoint(Mass destination, TextureName textureName){
		this.destination = destination;
		this.textureName = textureName;
		textureName.loadTexture();
	}
	@Override
	public void draw() {
		textureName.getTexture().bind();
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
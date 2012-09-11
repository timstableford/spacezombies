package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.Vector2d;

public class Waypoint implements Drawable{
	private Mass destination = null;
	private TextureName textureName = null;
	private Ship ship = null;
	public Waypoint(Ship ship, Mass destination, TextureName textureName){
		this.destination = destination;
		this.textureName = textureName;
		this.ship = ship;
		textureName.loadTexture();
	}
	@Override
	public void draw() {
		textureName.getTexture().bind();
		int h = textureName.getTexture().getImageHeight()/20;
		int w = textureName.getTexture().getImageWidth()/20;
		Vector2d a = new Vector2d(0,0);
		Vector2d.sub(new Vector2d(destination.getX(),destination.getY()), new Vector2d(ship.getX(),ship.getY()), a);
		a = a.unitVector();
		a = a.scale(50);
		int x = (int) (Display.getWidth()/2 + a.getX());
		int y = (int) (Display.getHeight()/2 + a.getY());
		float rotation = (float) a.getDegrees()+90;
		GL11.glTranslatef((x), (y), 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-(x), -(y), 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
    	glVertex2f((int)x-w,(int)y-h); //topleft
    	glTexCoord2f(1,0);
    	glVertex2f((int)x+w,(int)y-h); //top right
    	glTexCoord2f(1,1);
    	glVertex2f((int)x+w,(int)y+h); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2f((int)x-w,(int)y+h); //bottom left
    	glEnd();
    	GL11.glTranslatef((x), (y), 0);
		GL11.glRotatef(-rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-(x), -(y), 0);
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
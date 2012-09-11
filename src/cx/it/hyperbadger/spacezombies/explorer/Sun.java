package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import cx.it.hyperbadger.spacezombies.TextureName;

public class Sun extends Mass implements Drawable{
	private double planetRadius = 0;
	private TextureName textureName = null;
	public Sun(double mass, double x, double y, String name, String textureName, double planetRadius) {
		super(mass, x, y, name);
		this.planetRadius = planetRadius;
		//load texture
		this.textureName = new TextureName(textureName);
	}
	@Override
	public void draw() {
		if(textureName.getTexture()!=null){
			textureName.getTexture().bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f((float)(x-planetRadius),(float)(y-planetRadius)); //topleft
			glTexCoord2f(1,0);
			glVertex2f((float)(x+planetRadius),(float)(y-planetRadius)); //top right
			glTexCoord2f(1,1);
			glVertex2f((float)(x+planetRadius),(float)(y+planetRadius)); //bottom right
			glTexCoord2f(0,1);
			glVertex2f((float)(x-planetRadius),(float)(y+planetRadius)); //bottom left
			glEnd();
		}
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

package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sun extends Mass implements Drawable{
	private Texture texture = null;
	private int planetRadius = 0;
	public Sun(int mass, double x, double y, String name, String texture, int planetRadius) {
		super(mass, x, y, name);
		this.planetRadius = planetRadius;
		//load texture
		try {
			this.texture = TextureLoader.getTexture("PNG", ClassLoader.class.getResourceAsStream("/cx/it/hyperbadger/spacezombies/res/"+texture));
		} catch (IOException e) {
			System.err.println("Could not load texture: "+texture);
		}
	}
	@Override
	public void draw() {
		texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
    	glVertex2f((int)x-planetRadius,(int)y-planetRadius); //topleft
    	glTexCoord2f(1,0);
    	glVertex2f((int)x+planetRadius,(int)y-planetRadius); //top right
    	glTexCoord2f(1,1);
    	glVertex2f((int)x+planetRadius,(int)y+planetRadius); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2f((int)x-planetRadius,(int)y+planetRadius); //bottom left
    	glEnd();
	}

}

package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.math.BigDecimal;

import cx.it.hyperbadger.spacezombies.TextureName;

public class Sun extends Mass implements Drawable{
	private BigDecimal planetRadius;
	private TextureName textureName = null;
	public Sun(BigDecimal mass, BigDecimal x, BigDecimal y, String name, String textureName, BigDecimal planetRadius) {
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
			glVertex2f((x.floatValue()*scale.floatValue()-planetRadius.floatValue()*scale.floatValue()),
					(y.floatValue()*scale.floatValue()-planetRadius.floatValue()*scale.floatValue())); //topleft
			
			glTexCoord2f(1,0);
			glVertex2f((x.floatValue()*scale.floatValue()+planetRadius.floatValue()*scale.floatValue()),
					(y.floatValue()*scale.floatValue()-planetRadius.floatValue()*scale.floatValue())); //top right
			
			glTexCoord2f(1,1);
			glVertex2f((x.floatValue()*scale.floatValue()+planetRadius.floatValue()*scale.floatValue()),
					(y.floatValue()*scale.floatValue()+planetRadius.floatValue()*scale.floatValue())); //bottom right
			
			glTexCoord2f(0,1);
			glVertex2f((x.floatValue()*scale.floatValue()-planetRadius.floatValue()*scale.floatValue()),
					(y.floatValue()*scale.floatValue()+planetRadius.floatValue()*scale.floatValue())); //bottom left
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

package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Font;
import java.math.BigDecimal;
import java.math.MathContext;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;

import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.Vector2d;
import cx.it.hyperbadger.spacezombies.gui.GUIFont;

public class Waypoint implements Drawable{
	private Mass destination = null;
	private TextureName textureName = null;
	private Ship ship = null;
	private GUIFont guiFont;
	@SuppressWarnings("unchecked")
	public Waypoint(Ship ship, Mass destination, TextureName textureName){
		this.destination = destination;
		this.textureName = textureName;
		this.ship = ship;
		textureName.loadTexture();
		Font awtFont = new Font("Times New Roman", Font.BOLD, 12);
		guiFont = new GUIFont(awtFont);
		guiFont.getEffects().add(new ColorEffect(java.awt.Color.white));
	    guiFont.addAsciiGlyphs();
	    try {
	        guiFont.loadGlyphs();
	    } catch (SlickException ex) {
	       // Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	@Override
	public void draw() {
		if(destination instanceof Planet){
			Planet p = (Planet)destination;
			if(!(p.getParent() instanceof Sun)){
				if(p.getLocation().distance(p.getParent().getLocation())<ship.getLocation().distance(p.getLocation())){
					return;
				}
			}
		}
		textureName.getTexture().bind();
		double h = textureName.getTexture().getImageHeight()/20;
		double w = textureName.getTexture().getImageWidth()/20;
		Vector2d a = new Vector2d(0,0);
		Vector2d.sub(new Vector2d(destination.getX(),destination.getY()), new Vector2d(ship.getX(),ship.getY()), a);
		a = a.unitVector();
		a = a.scale(50);
		double x = (Display.getWidth()/2 + a.getX());
		double y = (Display.getHeight()/2 + a.getY());
		double rotation = a.getDegrees()+90;
		GL11.glTranslated((x), (y), 0);
		GL11.glRotated(rotation, 0f, 0f, 1f);
		GL11.glTranslated(-(x), -(y), 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
    	glVertex2d(x-w,y-h); //topleft
    	glTexCoord2f(1,0);
    	glVertex2d(x+w,y-h); //top right
    	glTexCoord2f(1,1);
    	glVertex2d(x+w,y+h); //bottom right
    	glTexCoord2f(0,1);
    	glVertex2d(x-w,y+h); //bottom left
    	glEnd();
    	GL11.glTranslated((x), (y), 0);
		GL11.glRotated(-rotation, 0f, 0f, 1f);
		GL11.glTranslated(-(x), -(y), 0);
		//distance measurement
		//double targetDistanceAU = ship.distance(destination)/149598000000.0;
		BigDecimal b = new BigDecimal(ship.distance(destination));
		BigDecimal c = new BigDecimal("149598000000");
		MathContext m = new MathContext(4);
		
		BigDecimal d = b.divide(c,m);
		b.setScale(4,BigDecimal.ROUND_HALF_UP);
		guiFont.drawString((float)x+15, (float)y, destination.getName()+"-"+d+"AU");
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
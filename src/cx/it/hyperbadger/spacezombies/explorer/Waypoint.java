package cx.it.hyperbadger.spacezombies.explorer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Font;
import java.math.BigDecimal;
import java.math.MathContext;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;

import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.Vector2BD;
import cx.it.hyperbadger.spacezombies.gui.GUIFont;

public class Waypoint implements Drawable{
	private Mass destination = null;
	private TextureName textureName = null;
	private Ship ship = null;
	private GUIFont guiFont;
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
		textureName.getTexture().bind();
		int h = textureName.getTexture().getImageHeight()/20;
		int w = textureName.getTexture().getImageWidth()/20;
		Vector2BD a = new Vector2BD();
		Vector2BD.sub(new Vector2BD(destination.getX(),destination.getY()), new Vector2BD(ship.getX(),ship.getY()), a);
		a = a.unitVector();
		a = a.scale(new BigDecimal("50"));
		int x = (int) (Display.getWidth()/2 + a.getX().floatValue());
		int y = (int) (Display.getHeight()/2 + a.getY().floatValue());
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
		//distance measurement
		MathContext mc = new MathContext(5);
		BigDecimal targetDistanceAU = ship.getMe().distance(destination.getMe()).divide(new BigDecimal("149598000000"),mc);
		guiFont.drawString(x+15, y, destination.getName()+" - "+targetDistanceAU+"AU");
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
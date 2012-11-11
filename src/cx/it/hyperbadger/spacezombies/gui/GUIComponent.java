package cx.it.hyperbadger.spacezombies.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.geom.Point2D;

import org.lwjgl.opengl.Display;

import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.explorer.Drawable;

public abstract class GUIComponent implements Drawable{
	protected Point2D topLeft = null, bottomRight = null;
	protected int top,bottom,left,right;
	protected String name = null;
	protected TextureName textureName = null;
	public GUIComponent(Point2D topLeft, Point2D bottomRight, TextureName textureName, String name){
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.name = name;
		this.textureName = textureName;
		//calculate real location
		calculateLocation();
		//load texture
	}
	public String getName(){
		return name;
	}
	public void setLocation(Point2D topLeft, Point2D bottomRight){
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		calculateLocation();
	}
	private void calculateLocation(){
		int dW = Display.getWidth();
		int dH = Display.getHeight();
		left = (int) ((topLeft.getX()*dW)/100);
		top = (int) ((topLeft.getY()*dH)/100);
		bottom = (int) ((bottomRight.getY()*dH)/100);
		right = (int) ((bottomRight.getX()*dW)/100);
	}
	@Override
	public void draw(){
		if(textureName.getTexture()!=null){
			textureName.getTexture().bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f(left,top); //topleft
			glTexCoord2f(1,0);
			glVertex2f(right,top); //top right
			glTexCoord2f(1,1);
			glVertex2f(right,bottom); //bottom right
			glTexCoord2f(0,1);
			glVertex2f(left,bottom); //bottom left
			glEnd();
		}
	}
	public boolean cursorHere(int mouseX, int mouseY){
		mouseY = Display.getHeight()-mouseY;
		if(mouseX<right&&mouseX>left&&mouseY<bottom&&mouseY>top){
			return true;
		}
		return false;
	}
	@Override
	public void setTexture(TextureName t){
		this.textureName = t;
	}
	@Override
	public TextureName getTexture(){
		return textureName;
	}
}

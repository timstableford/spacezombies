package cx.it.hyperbadger.spacezombies.gui;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import cx.it.hyperbadger.spacezombies.explorer.Drawable;

public abstract class GUI{
	protected ArrayList<GUIComponent> guiComponents;
	public GUI(){
		guiComponents = new ArrayList<GUIComponent>();
	}
	public void draw() {
		for(GUIComponent g: guiComponents){
			g.draw();
		}
	}
	public void addComponent(GUIComponent g){
		guiComponents.add(g);
	}
	public void poll(){
		while(Mouse.next()){
			for(GUIComponent g: guiComponents){
				if(g instanceof GUIButton){
					GUIButton b = (GUIButton)g;
					if(b.checkEvent()){
						break;
					}
				}
			}
		}
	}
	public boolean mouseInGUI(){
		for(GUIComponent g: guiComponents){
			if(g.cursorHere(Mouse.getX(),Mouse.getY())){
				return true;
			}
		}
		return false;
	}
}

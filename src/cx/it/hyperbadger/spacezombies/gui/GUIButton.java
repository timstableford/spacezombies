package cx.it.hyperbadger.spacezombies.gui;

import java.awt.geom.Point2D;

import org.lwjgl.input.Mouse;

import cx.it.hyperbadger.spacezombies.TextureName;

public class GUIButton extends GUIComponent {
	private GUIListener listener = null;
	private boolean eventButtonState = false;
	public GUIButton(Point2D topLeft, Point2D bottomRight, TextureName textureName, String name) {
		super(topLeft, bottomRight, textureName, name);
	}
	public void setListener(GUIListener e){
		listener = e;
	}
	public boolean checkEvent(){
		int mouseX = Mouse.getEventX();
		int mouseY = Mouse.getEventY();
		int eventButton = Mouse.getEventButton();
		boolean eventButtonStateNew = Mouse.getEventButtonState();
		if(cursorHere(mouseX,mouseY)){
			if(listener!=null){
				if(eventButtonState!=eventButtonStateNew){
					eventButtonState = eventButtonStateNew;
					GUIEvent event = GUIEvent.Error;
					if(eventButton==0){
						if(eventButtonState==true){
							event = GUIEvent.LeftButtonDown;
						}else{
							event = GUIEvent.LeftButtonUp;
						}
					}else if(eventButton==1){
						if(eventButtonState==true){
							event = GUIEvent.RightButtonDown;
						}else{
							event = GUIEvent.RightButtonUp;
						}
					}
					if(event!=GUIEvent.Error){
						listener.actionOccured(event, this);
					}
				}
			}
			return true;
		}
		return false;
	}
}

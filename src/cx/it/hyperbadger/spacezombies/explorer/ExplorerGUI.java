package cx.it.hyperbadger.spacezombies.explorer;

import java.awt.geom.Point2D;

import cx.it.hyperbadger.spacezombies.gui.GUI;
import cx.it.hyperbadger.spacezombies.gui.GUIButton;
import cx.it.hyperbadger.spacezombies.gui.GUIComponent;
import cx.it.hyperbadger.spacezombies.gui.GUIEvent;
import cx.it.hyperbadger.spacezombies.gui.GUIListener;

public class ExplorerGUI extends GUI implements GUIListener{
	public ExplorerGUI(){
		//initialize some components here and add them to an array
		
		//example button
		GUIButton b = new GUIButton(new Point2D.Double(10,10),new Point2D.Double(14,14),"spaceship.png","simple_button");
		//set its listener to this
		b.setListener(this);
		//add the component to the draw loop
		this.addComponent(b);
		
		
	}
	/**
	 * This is here because it implements listening
	 */
	@Override
	public void actionOccured(GUIEvent event, GUIComponent source) {
		System.out.println(source.getName()+" - "+event);
	}
}

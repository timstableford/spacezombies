package cx.it.hyperbadger.spacezombies.explorer;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import cx.it.hyperbadger.spacezombies.TextureBuffer;
import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.gui.GUI;
import cx.it.hyperbadger.spacezombies.gui.GUIButton;
import cx.it.hyperbadger.spacezombies.gui.GUIComponent;
import cx.it.hyperbadger.spacezombies.gui.GUIEvent;
import cx.it.hyperbadger.spacezombies.gui.GUIListener;

public class ExplorerGUI extends GUI implements GUIListener{
	private TextureBuffer textureBuffer = null;
	private Ship ship = null;
	private SolarSystem solarSystem = null;
	private ArrayList<Waypoint> waypoints = null;
	public ExplorerGUI(Ship ship, SolarSystem solarSystem){
		this.ship = ship;
		this.solarSystem = solarSystem;
		waypoints = new ArrayList<Waypoint>();
		//create a texture buffer and add textures
		ArrayList<TextureName> textures = new ArrayList<TextureName>();
		textures.add(new TextureName("spaceship.png"));
		textures.add(new TextureName("waypoint.png"));

		textureBuffer = new TextureBuffer(textures);
		//initialize some components here and add them to an array
		
		//example button
		GUIButton b = new GUIButton(new Point2D.Double(10,10),new Point2D.Double(14,14),textureBuffer.getTexture("spaceship.png"),"simple_button");
		//set its listener to this
		b.setListener(this);
		//add the component to the draw loop
		this.addComponent(b);
		
		//waypoints
		waypoints.add(new Waypoint(solarSystem.findMass("Sun"),textureBuffer.getTexture("waypoint.png")));
	}
	/**
	 * This is here because it implements listening
	 */
	@Override
	public void actionOccured(GUIEvent event, GUIComponent source) {
		System.out.println(source.getName()+" - "+event);
	}
	@Override
	public void draw() {
		for(GUIComponent g: guiComponents){
			g.draw();
		}
		for(Waypoint w: waypoints){
			w.draw();
		}
	}
}

package cx.it.hyperbadger.spacezombies.explorer;

import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;

import cx.it.hyperbadger.spacezombies.Game;
import cx.it.hyperbadger.spacezombies.TextureBuffer;
import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.gui.GUI;
import cx.it.hyperbadger.spacezombies.gui.GUIButton;
import cx.it.hyperbadger.spacezombies.gui.GUIComponent;
import cx.it.hyperbadger.spacezombies.gui.GUIEvent;
import cx.it.hyperbadger.spacezombies.gui.GUIImage;
import cx.it.hyperbadger.spacezombies.gui.GUIListener;
import cx.it.hyperbadger.spacezombies.gui.Font.SZFont;
import cx.it.hyperbadger.spacezombies.gui.Font.SZString;

public class ExplorerGUI extends GUI implements GUIListener{
	private TextureBuffer textureBuffer = null;
	private Ship ship = null;
	private SolarSystem solarSystem = null;
	private ArrayList<Waypoint> waypoints = null;
	private Game game;
	private SZFont szf;
	private LinkedList<SZString> strings;
	private ShipControl shipControl;
	public ExplorerGUI(ShipControl shipControl, SolarSystem solarSystem, Game game){
		this.ship = shipControl.getShip();
		this.solarSystem = solarSystem;
		this.game = game;
		this.shipControl = shipControl;
		waypoints = new ArrayList<Waypoint>();
		strings = new LinkedList<SZString>();
		//some useful data
		double shipVelocity = ship.getVelocity();
		double shipForce = ship.getForce();
		// ship.setForce(10000);
		// ship.setAuto(true/false);
		// ship.setAutoVelocity(autoVelocity);
		//create a texture buffer and add textures
		ArrayList<TextureName> textures = new ArrayList<TextureName>();
		textures.add(new TextureName("spaceship.png"));
		textures.add(new TextureName("waypoint.png"));
		textures.add(new TextureName("button.png"));
		textures.add(new TextureName("distance.or.current speed field.png"));
		textures.add(new TextureName("pointer button.png"));
		textures.add(new TextureName("ship full shields status.png"));
		textures.add(new TextureName("ship outline.png"));
		textures.add(new TextureName("velocity bar.png"));
		textureBuffer = new TextureBuffer(textures);
		//initialize some components here and add them to an array
		
		//(hopefully) the current velocity display
		/*
		 * 1.set text field for current speed
		 * 2.get current speed
		 * 3.print current speed inside text field
		 * 4.call for and print speed field .png
		 */
		GUIImage speedField = new GUIImage(new Point2D.Double(2,5), new Point2D.Double(19,13), textureBuffer.getTexture("distance.or.current speed field.png"), "current speed field");
		GUIImage distanceField = new GUIImage(new Point2D.Double(2,12), new Point2D.Double(19,20), textureBuffer.getTexture("distance.or.current speed field.png"), "distance field");
		GUIImage shipOutline= new GUIImage(new Point2D.Double(85,5), new Point2D.Double(105,20), textureBuffer.getTexture("ship outline.png"), "ship outline");
		GUIImage shipFullShields= new GUIImage(new Point2D.Double(85,5), new Point2D.Double(104,19), textureBuffer.getTexture("ship full shields status.png"), "ship full shields");
		
		this.addComponent(speedField);
		this.addComponent(distanceField);
		this.addComponent(shipOutline);
		this.addComponent(shipFullShields);
		
		/*//example button
		GUIButton b = new GUIButton(new Point2D.Double(10,10),new Point2D.Double(14,14),textureBuffer.getTexture("spaceship.png"),"simple_button");
		//set its listener to this
		b.setListener(this);
		//add the component to the draw loop
		this.addComponent(b);
		*/
		
	
		//text example
		szf = new SZFont("/cx/it/hyperbadger/spacezombies/gui/Font/SZM/");
		strings.add(new SZString("TEST", szf, new Point2D.Double(20,10), new Point2D.Double(100,14)));
		//waypoints
	    //automatically add all bodies except ships in the system
		for(Mass m: solarSystem.getMasses()){
			if((m instanceof Drawable)&&((m instanceof Sun)||(m instanceof Planet))){
				waypoints.add(new Waypoint(ship, m, textureBuffer.getTexture("waypoint.png")));
			}
		}
		//setting the game scale
		this.setScale(1);
		//this.setScale(0.1);
		
		
		//setting the focus
		//game.setFocus(Mass m) eg ship findMass("Earth") etc
		//game.setFocus(Point2D p)
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
		for(SZString s: strings){
			s.draw();
		}
		//the rest of the text example
		Point2D topLeft = new Point2D.Double(20,10);
		Point2D bottomRight = new Point2D.Double(45,15);
		//guiFont.drawString(topLeft, bottomRight, "This is an example text");
	}
	@Override
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
			if(!mouseInGUI()){
				shipControl.updatePeriodic();
			}
		}
	}
}

package cx.it.hyperbadger.spacezombies.explorer;

import java.util.ArrayList;

public class SolarSystem implements Drawable, Moveable{
	private ArrayList<Mass> masses;
	private String name;
	public SolarSystem(String name, ArrayList<Mass> masses){
		this.name = name;
		this.masses = masses;
	}
	public Mass findMass(String n){
		for(Mass p: masses){
			if(p.getName().equals(n)){
				return p;
			}
		}
		return null;
	}
	public void draw(){
		for(Mass p: masses){
			if(p instanceof Drawable){
				Drawable d = (Drawable) p;
				d.draw();
			}
		}
	}
	public void move(){
		for(Mass p: masses){
			if(p instanceof Moveable){
				Moveable m = (Moveable) p;
				m.move();
			}
		}
	}
	public ArrayList<Mass> getMasses(){
		return masses;
	}
	public String getName(){
		return name;
	}
}

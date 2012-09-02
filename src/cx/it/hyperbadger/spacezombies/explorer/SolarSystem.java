package cx.it.hyperbadger.spacezombies.explorer;

import java.util.ArrayList;

public class SolarSystem implements Drawable, Moveable{
	private ArrayList<Planet> planets;
	private String name;
	public SolarSystem(String name, ArrayList<Planet> planets){
		this.name = name;
		this.planets = planets;
	}
	public Planet findPlanet(String n){
		for(Planet p: planets){
			if(p.getName().equals(n)){
				return p;
			}
		}
		return null;
	}
	public void draw(){
		for(Planet p: planets){
			if(p instanceof Drawable){
				p.draw();
			}
		}
	}
	public void move(){
		for(Planet p: planets){
			if(p instanceof Moveable){
				p.move();
			}
		}
	}
	public ArrayList<Planet> getPlanets(){
		return planets;
	}
	public ArrayList<Mass> getMasses(){
		ArrayList<Mass> m = new ArrayList<Mass>();
		for(Planet p: planets){
			if(p instanceof Mass){
				m.add(p);
			}
		}
		return m;
	}
}

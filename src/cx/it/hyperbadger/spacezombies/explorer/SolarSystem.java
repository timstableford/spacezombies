package cx.it.hyperbadger.spacezombies.explorer;

import java.util.ArrayList;

public class SolarSystem {
	private ArrayList<Planet> planets;
	private String name;
	public SolarSystem(String name, ArrayList<Planet> planets){
		this.name = name;
		this.planets = planets;
	}
}

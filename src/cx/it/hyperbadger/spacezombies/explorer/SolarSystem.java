package cx.it.hyperbadger.spacezombies.explorer;

import java.util.ArrayList;


public class SolarSystem implements Moveable{
	private ArrayList<Mass> masses;
	private String name;
	public SolarSystem(String name){
		this.name = name;
		masses = new ArrayList<Mass>();
	}
	public void addMass(Mass m){
		masses.add(m);
		loadTextures();
	}
	private void loadTextures(){
		for(Mass m: masses){
			if(m instanceof Drawable){
				Drawable t = (Drawable)m;
				t.getTexture().loadTexture();
			}
		}
	}
	public Mass findMass(String n){
		for(Mass p: masses){
			if(p.getName().equals(n)){
				return p;
			}
		}
		return null;
	}
	public void setShipScale(double d){
		for(Mass p: masses){
			if(p instanceof Ship){
				p.setScale(d);
			}
		}
	}
	public void draw(){
		for(Mass p: masses){
			if(p instanceof Drawable && !(p instanceof Ship)){
				Drawable d = (Drawable) p;
				d.draw();
			}
		}
	}
	public void drawShips(){
		for(Mass p: masses){
			if(p instanceof Ship){
				Drawable d = (Drawable) p;
				d.draw();
			}
		}
	}
	@Override
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
	public void setScale(double scale){
		for(Mass m: masses){
			m.setScale(scale);
		}
	}
	public ArrayList<Collidable> getCollidable(){
		ArrayList<Collidable> c = new ArrayList<Collidable>();
		for(Mass p: masses){
			c.add(p);
		}
		return c;
	}
}

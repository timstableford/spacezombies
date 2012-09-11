package cx.it.hyperbadger.spacezombies;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureName {
	private Texture texture = null;
	private String name = null;
	public TextureName(String name){
		this.name = name;
	}
	public void setTexture(Texture t){
		texture = t;
	}
	public Texture getTexture(){
		return texture;
	}
	public String getName(){
		return name;
	}
	public void loadTexture(){
		if(texture==null){
			try {
				setTexture(TextureLoader.getTexture("PNG", ClassLoader.class.getResourceAsStream("/cx/it/hyperbadger/spacezombies/res/"+getName())));
				System.out.println("Texture loaded: "+getName());
			} catch (IOException e) {
				System.err.println("Could not load texture: "+getName());
			}
			System.out.println("Texture loaded: "+name);
		}
	}
}

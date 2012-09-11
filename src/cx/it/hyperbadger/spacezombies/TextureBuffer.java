package cx.it.hyperbadger.spacezombies;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureBuffer {
	private ArrayList<TextureName> textures;
	public TextureBuffer(ArrayList<TextureName> textures){
		this.textures = textures;
		loadTextures();
	}
	private void loadTextures(){
		for(TextureName t: textures){
			t.loadTexture();
		}
	}
	public TextureName getTexture(String name){
		for(TextureName t: textures){
			if(t.getName().equals(name)){
				return t;
			}
		}
		return null;
	}
}

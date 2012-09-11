package cx.it.hyperbadger.spacezombies;

import java.util.ArrayList;

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

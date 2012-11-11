package cx.it.hyperbadger.spacezombies.gui.Font;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SZFont {
	private HashMap<Character,SZLetter> letters;
	private String fontPackage;
	public SZFont(String fontPackage){
		letters = new HashMap<Character,SZLetter>();
		this.fontPackage = fontPackage;
	}
	private SZLetter getLetter(char input){
		if(letters.containsKey(input)){
			return letters.get(input);
		}
		BufferedImage i;
		try {
			i = ImageIO.read(ClassLoader.class.getResourceAsStream(fontPackage+input+".png"));
			SZLetter t = new SZLetter(i,input);
			letters.put(input, t);
			return t;
		} catch (IOException e) {
			return null; //could not find image
		}
	}
	public BufferedImage charToImage(char input){
		return getLetter(input).getImage();
	}
}

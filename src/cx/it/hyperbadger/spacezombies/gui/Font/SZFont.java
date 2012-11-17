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
		String letter = null;
		switch(input){
		case '.':
			letter = "dot";
			break;
		default:
			letter = input+"";
			letter = letter.toUpperCase();
		}
		BufferedImage i;
		try {
			String source = fontPackage+letter+".png";
			System.out.println(source);
			i = ImageIO.read(ClassLoader.class.getResourceAsStream(source));
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

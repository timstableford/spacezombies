package cx.it.hyperbadger.spacezombies.gui.Font;

import java.awt.image.BufferedImage;

public class SZLetter {
	private BufferedImage image;
	private char letter;
	public SZLetter(BufferedImage image, char letter){
		this.image = image;
		this.letter = letter;
	}
	public BufferedImage getImage(){
		return image;
	}
	public char getLetter(){
		return letter;
	}
}

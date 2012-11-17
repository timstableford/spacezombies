package cx.it.hyperbadger.spacezombies.gui.Font;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import cx.it.hyperbadger.spacezombies.TextureName;
import cx.it.hyperbadger.spacezombies.gui.GUIComponent;

public class SZString extends GUIComponent{
	private BufferedImage image = null;
	private SZFont font;
	public SZString(String input, SZFont font, Point2D topLeft, Point2D bottomRight){
		super(topLeft,bottomRight,null,input);
		this.name = input;
		this.font = font;
		this.textureName = new TextureName(name);
		this.image = render();
	}
	public BufferedImage render(){
		BufferedImage ret = this.image;
		for(char c: name.toCharArray()){
			BufferedImage toAdd = font.charToImage(c);
			if(toAdd!=null){
				ret = append(ret,toAdd);
				textureName.loadTexture(ret);
			}
		}
		return ret;
	}
	public void setText(String text){
		this.name = text;
		render();
	}
	public BufferedImage getImage(){
		if(image==null){ image=render(); }
		return image;
	}
	public SZString append(SZString other){
		SZString ret = new SZString(name+other.name,font,this.topLeft,
				new Point2D.Double(this.bottomRight.getX()+other.bottomRight.getX(),this.bottomRight.getY()));
		ret.image = append(this.getImage(),other.getImage());
		ret.textureName.loadTexture(this.image);
		return ret;
	}
	private BufferedImage append(BufferedImage start, BufferedImage end){
		if(start==null){ return end; }
		BufferedImage ret = new BufferedImage(start.getWidth()+end.getWidth(),start.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = ret.createGraphics();
		g2.drawImage(start, 0, 0, start.getWidth(), start.getHeight(), 0, 0, start.getWidth(), start.getHeight(), null);
		g2.drawImage(end, start.getWidth(), 0, start.getWidth()+end.getWidth(), start.getHeight(), 0, 0, end.getWidth(), end.getHeight(), null);
		return ret;
	}
}

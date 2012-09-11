package cx.it.hyperbadger.spacezombies.gui;

import java.awt.geom.Point2D;

import cx.it.hyperbadger.spacezombies.TextureName;

public class GUIImage extends GUIComponent{

	public GUIImage(Point2D topLeft, Point2D bottomRight, TextureName textureName, String name) {
		super(topLeft, bottomRight, textureName, name);
	}

}

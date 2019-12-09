package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Shapee {
 Point p = new Point();
	protected Map<String, Double> pb = new HashMap<String, Double>();
	protected Color c = Color.blue;
	protected Color fc = Color.blue;


	public void setPosition(Point p) {
		if(p!=null)
		this.p = p;
	}

	public Point getPosition() {
		
		return p;
	}

	public void setProperties(java.util.Map<String, Double> pb) {
		if(pb!=null)
		this.pb = pb;
	}

	public java.util.Map<String, Double> getProperties() {
		return pb;
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public Color getColor() {
		return c;
	}

	public void setFillColor(Color fc) {
		this.fc = fc;
	}

	public java.awt.Color getFillColor() {
		return fc;
	}

}
package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Ellipse extends Shapee implements Shape {
	public Ellipse() {
		pb.put("width", 100.0);
		pb.put("height", 50.0);
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(fc);
		canvas.fillOval(p.x, p.y, pb.get("width").intValue(), pb.get("height").intValue());
		canvas.setColor(c);
		canvas.drawOval(p.x, p.y, pb.get("width").intValue(), pb.get("height").intValue());
	}

	public Object clone() throws CloneNotSupportedException{
		Ellipse nE = new Ellipse();
		nE.setColor(c);
		nE.setFillColor(fc);
		nE.setPosition(p);
		nE.setProperties(pb);
		return nE;
		
	}

}
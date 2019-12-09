package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Rectangle extends Shapee implements Shape {
	public Rectangle() {
		pb.put("width", 50.0);
		pb.put("height", 50.0);
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(fc);
		canvas.fillRect(p.x, p.y, pb.get("width").intValue(), pb.get("height").intValue());
		canvas.setColor(c);
		canvas.drawRect(p.x, p.y, pb.get("width").intValue(), pb.get("height").intValue());
	}

	public Object clone() throws CloneNotSupportedException{
		Rectangle nR = new Rectangle();
		nR.setColor(c);
		nR.setFillColor(fc);
		nR.setPosition(p);
		nR.setProperties(pb);
		return nR;
		
	}

}
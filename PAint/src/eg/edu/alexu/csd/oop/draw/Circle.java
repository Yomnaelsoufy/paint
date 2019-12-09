package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;


public class Circle extends Shapee implements Shape  {
	public Circle() {
		pb.put("radius", 50.0);
	}

	public void draw(Graphics canvas) {
		canvas.setColor(fc);
		canvas.fillOval(p.x, p.y, pb.get("radius").intValue() * 2, pb.get("radius").intValue() * 2);
		canvas.setColor(c);
		canvas.drawOval(p.x, p.y, pb.get("radius").intValue() * 2, pb.get("radius").intValue() * 2);
	}
	
	public Object clone() throws CloneNotSupportedException{
		Circle nc = new Circle();
		nc.setColor(c);
		nc.setFillColor(fc);
		nc.setPosition(p);
		nc.setProperties(pb);
		return nc;
		
	}

}
package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Triangle extends Shapee implements Shape {
	public Triangle() {
		pb.put("length", 50.0);
		
	}
	
	public void draw(Graphics canvas) {
		int x[] = {p.x, p.x+pb.get("length").intValue()/2,p.x+pb.get("length").intValue()};
		int y[] = {p.y, (int)(p.y-Math.sqrt(3.0/4.0)*pb.get("length")),p.y};
		
		canvas.setColor(fc);
		canvas.fillPolygon(x, y, 3);
		canvas.setColor(c);
		canvas.drawPolygon(x, y, 3);
	}

	public Object clone() throws CloneNotSupportedException{
		Triangle nT = new Triangle();
		nT.setColor(c);
		nT.setFillColor(fc);
		nT.setPosition(p);
		nT.setProperties(pb);
		return nT;
		
	}

}
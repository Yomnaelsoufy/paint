package eg.edu.alexu.csd.oop.draw;

import java.awt.Font;
import java.awt.Graphics;

public class Line extends Shapee implements Shape {
	public Line() {
		pb.put("length", 50.0);
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(c);
		canvas.drawLine(p.x, p.y, pb.get("length").intValue()+p.x, p.y);
		
	}

	public Object clone() throws CloneNotSupportedException{
		Line nL = new Line();
		nL.setColor(c);
		nL.setFillColor(fc);
		nL.setPosition(p);
		nL.setProperties(pb);
		return nL;
		
	}

}
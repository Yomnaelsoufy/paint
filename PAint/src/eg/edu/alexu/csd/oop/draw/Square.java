package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class Square extends Shapee implements Shape {
	public Square() {
		pb.put("width", 50.0);
	}
	
	public void draw(Graphics canvas) {
		canvas.setColor(fc);
		canvas.fillRect(p.x, p.y, pb.get("width").intValue(), pb.get("width").intValue());
		canvas.setColor(c);
System.out.println(p.x);
System.out.println(p.y);
		canvas.drawRect(p.x, p.y, pb.get("width").intValue(), pb.get("width").intValue());
	}

	public Object clone() throws CloneNotSupportedException{
		Square nS = new Square();
		nS.setColor(c);
		nS.setFillColor(fc);
		nS.setPosition(p);
		nS.setProperties(pb);
		return nS;
		
	}

}
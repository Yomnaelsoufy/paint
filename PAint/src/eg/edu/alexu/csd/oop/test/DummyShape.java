//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package eg.edu.alexu.csd.oop.test;

import eg.edu.alexu.csd.oop.draw.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

public class DummyShape implements Shape, Cloneable {
    private Point position;
    private Map<String, Double> properties;
    private Color color;
    private Color fColor;
    private boolean tryDraw;

    public DummyShape() {
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }

    public Map<String, Double> getProperties() {
        return this.properties;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setFillColor(Color fcolor) {
        this.fColor = fcolor;
    }

    public Color getFillColor() {
        return this.fColor;
    }

    public void draw(Graphics canvas) {
        this.tryDraw = true;
    }

    public boolean isDraw() {
        return this.tryDraw;
    }
}

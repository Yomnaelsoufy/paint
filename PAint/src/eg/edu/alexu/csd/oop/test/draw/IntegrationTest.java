package eg.edu.alexu.csd.oop.test.draw;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import javax.swing.DebugGraphics;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.gui;
import eg.edu.alexu.csd.oop.test.DummyShape;
import eg.edu.alexu.csd.oop.test.TestRunner;



public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return DrawingEngine.class;
    }
    
    @org.junit.Test
    public void testCreation() {
    	gui instance = new gui() ;
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

    
    @org.junit.Test
    public void testGraphics() {
       gui instance = new  gui();
        try {
            DummyShape shape = new DummyShape();
            Graphics2D g2=null;
            shape.setColor(Color.RED);
            shape.setFillColor(Color.RED);
            shape.setPosition(new Point(0,0));
            shape.setProperties(new HashMap<String, Double>());
            instance.addShape(shape);
            instance.refresh(g2);
            assertTrue("Failed to use dummy Graphics object to draw dummy Shape", shape.isDraw());
        } catch (Throwable e) {
            TestRunner.fail("Failed to use dummy Graphics object", e);
        }
    }
    
   /*    @org.junit.Test
 public void testCreateShapes(){
    	gui instance = new gui();
        List<Class<? extends Shape>> supportedShapes = instance.getSupportedShapes();
        assertNotNull("No supported shapes returned, check getSupportedShapes function!", supportedShapes);
        assertFalse("No supported shapes returned, check getSupportedShapes function!", supportedShapes.isEmpty());
        for(Class<? extends Shape> shapeClass : supportedShapes){
            try {
                Shape shape = shapeClass.newInstance();
                assertNotNull("Failed to create shape", shape);
            } catch (Exception e) {
                TestRunner.fail("Failed to create shape", e);
            }
        }
    }*/

}
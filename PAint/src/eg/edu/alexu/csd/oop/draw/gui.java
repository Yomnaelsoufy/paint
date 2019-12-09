package eg.edu.alexu.csd.oop.draw;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.security.auth.Refreshable;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
public class gui extends JFrame implements ActionListener, DrawingEngine {
    private int COUNT = 0;
    private int CC = 0;
    private int x, y;
    private double r, l, w, ll, tl, ls, r1, r2;
    private Graphics2D g2;
    private Vector<Shape> Move = new Vector<Shape>();
    private Vector<Integer> op = new Vector<Integer>();
    private int Cmove = -1;
    private Vector<Shape> memory = new Vector<Shape>();
    private JPanel p;
    private JButton circle, square, triangle, rectangle, ellipse, line;
    private JButton bluecolor, graycolor, blackcolor, cyancolor, pinkcolor, redcolor;
    private JButton uNdo, rEdo;
    private JButton btnMove, btnResize, btnSave, btnLoad;
    private String sh = new String();
    private JButton btnDelete;
    private int lr, lu;
 
    public void addShape(Shape shape) {
    	shape.draw(g2);
        memory.add(shape);
        Move.add(Cmove + 1, shape);
        op.add(Cmove + 1, 1);
        Cmove++;
        if (lu > 0)
            lu--;
        if (lr > 0)
            lr--;
        refresh(g2);
    }
 
    public void updateShape(Shape oldShape, Shape newShape) {
        memory.removeElement(oldShape);
        memory.add(newShape);
        Move.add(Cmove + 1, oldShape);
        Move.add(Cmove + 2, newShape);
        op.add(Cmove + 1, 3);
        op.add(Cmove + 2, 3);
        Cmove += 2;
        refresh(g2);
    }
 
    public Shape[] getShapes() {
        Shape[] s = new Shape[memory.size()];
        memory.copyInto(s);
        return s;
    }
 
    public void refresh(Graphics g2) {
        if (g2 != null) {
            g2.clearRect(120, 0, 1000, 1000);
            for (int i = 0; i < memory.size(); i++) {
                memory.elementAt(i).draw(g2);
            }
        }
    }
 
    public void removeShape(Shape shape) {
        memory.remove(shape);
        op.add(Cmove + 1, 2);
        Move.add(Cmove + 1, shape);
        Cmove++;
        refresh(g2);
 
    }
 
    public java.util.List<Class<? extends Shape>> getSupportedShapes() {
        return null;
 
    }
 
    public void installPluginShape(String jarPath) {
    }
    public void undo() {
        if (lu == 20) {
            return;
        }
        if (Cmove < 0 || Move.isEmpty()) {
            g2.clearRect(120, 0, 1000, 1000);
            JOptionPane.showMessageDialog(null, "Done!");
        } else {
            lu++;
            if (op.elementAt(Cmove) == 1) {
                memory.remove(Move.elementAt(Cmove));
                op.set(Cmove, 2);
                refresh(g2);
                Cmove--;
            } else if (op.elementAt(Cmove) == 2) {
                memory.add(Move.elementAt(Cmove));
                op.set(Cmove, 1);
                refresh(g2);
                Cmove--;
            } else if (op.elementAt(Cmove) == 3) {
                memory.remove(Move.elementAt(Cmove));
                memory.add(Move.elementAt(Cmove - 1));
                refresh(g2);
                Cmove -= 2;
 
            }
        }
 
    }
 
    public void redo() {
        if (lr == 20) {
            return;
        }
        if (Cmove == Move.size() - 1) {
            JOptionPane.showMessageDialog(null, "Done!");
        } else {
            lr++;
            lu--;
            if (op.elementAt(Cmove + 1) == 1) {
                memory.removeElement(Move.elementAt(Cmove + 1));
                op.set(Cmove + 1, 2);
                refresh(g2);
                Cmove++;
            } else if (op.elementAt(Cmove + 1) == 2) {
                memory.add(Move.elementAt(Cmove + 1));
                op.set(Cmove + 1, 1);
                refresh(g2);
                Cmove++;
            } else if (op.elementAt(Cmove + 1) == 3) {
                memory.removeElement(Move.elementAt(Cmove + 1));
                memory.add(Move.elementAt(Cmove + 2));
                refresh(g2);
                Cmove += 2;
 
            }
 
        }
    }
 
    public void save(String path) {
        try {
            FileOutputStream Route = new FileOutputStream(new File(path));
            XMLEncoder input = new XMLEncoder(Route);
            input.writeObject(memory.size());
            for (int i = 0; i < memory.size(); i++) {
                input.writeObject(memory.get(i));
            }
            input.close();
            Route.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Try again.");
            e.printStackTrace();
        }
    }
 
    public void load(String path) {
        System.out.println(memory.size());
        try {
            String type = "";
            int j = path.lastIndexOf('.');
            if (j >= 0) {
                type = path.substring(j + 1);
            }
            if (!type.equals("xml")) {
                JOptionPane.showMessageDialog(null, "Invalid file type, Try again.");
            } else {
                memory.removeAllElements();
                FileInputStream Route = new FileInputStream(new File(path));
                XMLDecoder op = new XMLDecoder(Route);
                String s = op.readObject().toString();
                for (int i = 0; i < Integer.parseInt(s); i++) {
                    memory.add((Shape) op.readObject());
                }
                CC = 12;
                repaint();
                op.close();
                Route.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Try again.");
            e.printStackTrace();
        }
 
    }
 
    public void paint(Graphics g) {
        g2 = (Graphics2D) g;
        if (COUNT == 1) {
            Circle c = new Circle();
            c.setColor(Color.black);
            c.setFillColor(Color.white);
            c.pb.replace("radius", r);
            Point n1 = new Point();
            n1.x = x - c.pb.get("radius").intValue();
            n1.y = y - c.pb.get("radius").intValue() / 3;
            c.setPosition(n1);
            addShape(c);
            COUNT = 0;
        } else if (COUNT == 2) {
            Rectangle c = new Rectangle();
            c.setColor(Color.black);
            c.setFillColor(Color.white);
            c.pb.replace("width", w);
            c.pb.replace("height", l);
            Point n1 = new Point();
            n1.x = x - 1;
            n1.y = y + 32;
            c.setPosition(n1);
            addShape(c);
            COUNT = 0;
        } else if (COUNT == 3) {
            Square c = new Square();
            c.setColor(Color.black);
            c.setFillColor(Color.white);
            c.pb.replace("width", ls);
            Point n1 = new Point();
            n1.x = x + 2;
            n1.y = y + 30;
            c.setPosition(n1);
            addShape(c);
            COUNT = 0;
        } else if (COUNT == 4) {
            Ellipse c = new Ellipse();
            c.setColor(Color.black);
            c.setFillColor(Color.white);
            c.pb.replace("width", r1);
            c.pb.replace("height", r2);
            Point n1 = new Point();
            n1.x = x + 1;
            n1.y = y + 10;
            c.setPosition(n1);
            addShape(c);
            COUNT = 0;
        } else if (COUNT == 5) {
            Triangle c = new Triangle();
            c.setColor(Color.black);
            c.setFillColor(Color.white);
            c.pb.replace("length", tl);
            Point n1 = new Point();
            n1.x = x;
            n1.y = y + 31;
            c.setPosition(n1);
            addShape(c);
            COUNT = 0;
        } else if (COUNT == 6) {
            Line c = new Line();
            c.setColor(Color.black);
            c.setFillColor(Color.white);
            c.pb.replace("length", ll);
            Point n1 = new Point();
            n1.x = x;
            n1.y = y + 31;
            c.setPosition(n1);
            addShape(c);
            COUNT = 0;
        } else if (CC == 1) {
            Shape tmp = null;
            try {
                tmp = (Shape) memory.lastElement().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            tmp.setFillColor(Color.black);
            tmp.setColor(Color.black);
            updateShape(memory.lastElement(), tmp);
            CC = 0;
        } else if (CC == 2) {
            Shape tmp = null;
            try {
                tmp = (Shape) memory.lastElement().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            tmp.setFillColor(Color.BLUE);
            tmp.setColor(Color.BLUE);
            updateShape(memory.lastElement(), tmp);
            CC = 0;
        } else if (CC == 3) {
            Shape tmp = null;
            try {
                tmp = (Shape) memory.lastElement().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            tmp.setFillColor(Color.PINK);
            tmp.setColor(Color.PINK);
            updateShape(memory.lastElement(), tmp);
            CC = 0;
        } else if (CC == 4) {
            Shape tmp = null;
            try {
                tmp = (Shape) memory.lastElement().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            tmp.setFillColor(Color.RED);
            tmp.setColor(Color.RED);
            updateShape(memory.lastElement(), tmp);
            CC = 0;
        } else if (CC == 5) {
            Shape tmp = null;
            try {
                tmp = (Shape) memory.lastElement().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            tmp.setFillColor(Color.gray);
            tmp.setColor(Color.gray);
            updateShape(memory.lastElement(), tmp);
            CC = 0;
        } else if (CC == 6) { 
            Shape tmp = null;
            try {
                tmp = (Shape) memory.lastElement().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            tmp.setFillColor(Color.cyan);
            tmp.setColor(Color.cyan);
            updateShape(memory.lastElement(), tmp);
            CC = 0;
        } else if (CC == 7) { //undo
            undo();
            CC = 0;
        } else if (CC == 8) { //redo
            redo();
            CC = 0;
        } else if (CC == 9) { //move
            char s = sh.charAt(0);
            int c = Integer.parseInt(sh.substring(1));
            if (!Character.isUpperCase(s) || c == 0) {
                JOptionPane.showMessageDialog(null, "Error.", "", JOptionPane.ERROR_MESSAGE);
                CC = 0;
                repaint();
            }
            int j = 0;
            for (int i = 0; i < memory.size(); i++) {
                if (memory.elementAt(i).toString().charAt(26) == s) {
                    j++;
                    if (j == c) {
                        Point p = new Point();
                        if (s == 'E' || s == 'C') {
                            p.setLocation(x - 4, y + 10);
                        } else {
                            p.setLocation(x, y + 31);
                        }
 
                        Shape tmp = null;
                        try {
                            tmp = (Shape) memory.elementAt(i).clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        tmp.setPosition(p);
                        updateShape(memory.elementAt(i), tmp);
                        break;
                    }
                }
            }
 
        } else if (CC == 10) { //resize
            char s = sh.charAt(0);
            int c = Integer.parseInt(sh.substring(1));
            if (!Character.isUpperCase(s) || c == 0) {
                JOptionPane.showMessageDialog(null, "Error. Try again", "?", JOptionPane.ERROR_MESSAGE);
                CC = 0;
                repaint();
            }
            int j = 0;
            double rc = -1, rc2 = -1;
            for (int i = 0; i < memory.size(); i++) {
                if (memory.elementAt(i).toString().charAt(26) == s) {
                    j++;
                    if (j == c) {
                        Shape tmp = null;
                        try {
                            tmp = (Shape) memory.elementAt(i).clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        if (s == 'C') {
                            String ans = JOptionPane.showInputDialog("Enter the radius:");
                            try {
                                rc = Double.parseDouble(ans);
                            } catch (Exception e3) {
                                JOptionPane.showMessageDialog(null, "You should enter a num. Try again", "ERROR!",
                                        JOptionPane.ERROR_MESSAGE);
                                CC = 0;
                            }
                            if (rc != -1) {
                                Map<String, Double> pb = new HashMap<String, Double>();
                                pb.put("radius", rc);
                                tmp.setProperties(pb);
                                updateShape(memory.elementAt(i), tmp);
                            }
                        } else if (s == 'E') {
                            String ans = JOptionPane.showInputDialog("Enter the radius1:");
                            String ans1 = JOptionPane.showInputDialog("Enter the radius2:");
                            try {
                                rc = Double.parseDouble(ans);
                                rc2 = Double.parseDouble(ans1);
                            } catch (Exception e3) {
                                JOptionPane.showMessageDialog(null, "You should enter a num. Try again", "ERROR!",
                                        JOptionPane.ERROR_MESSAGE);
                                CC = 0;
                            }
                            if (rc != -1 && rc2 != -1) {
                                Map<String, Double> pb = new HashMap<String, Double>();
                                pb.put("width", rc);
                                pb.put("height", rc2);
                                tmp.setProperties(pb);
                                updateShape(memory.elementAt(i), tmp);
                            }
                        } else if (s == 'T') {
                            String ans = JOptionPane.showInputDialog("Enter the length:");
                            try {
                                rc = Double.parseDouble(ans);
                            } catch (Exception e3) {
                                JOptionPane.showMessageDialog(null, "You should enter a num. Try again", "ERROR!",
                                        JOptionPane.ERROR_MESSAGE);
                                CC = 0;
                            }
                            if (rc != -1) {
                                Map<String, Double> pb = new HashMap<String, Double>();
                                pb.put("length", rc);
                                tmp.setProperties(pb);
                                updateShape(memory.elementAt(i), tmp);
                            }
                        } else if (s == 'S') {
                            String ans = JOptionPane.showInputDialog("Enter the length:");
                            try {
                                rc = Double.parseDouble(ans);
                            } catch (Exception e3) {
                                JOptionPane.showMessageDialog(null, "You should enter a num. Try again", "ERROR!",
                                        JOptionPane.ERROR_MESSAGE);
                                CC = 0;
                            }
                            if (rc != -1) {
                                Map<String, Double> pb = new HashMap<String, Double>();
                                pb.put("width", rc);
                                tmp.setProperties(pb);
                                updateShape(memory.elementAt(i), tmp);
                            }
                        } else if (s == 'R') {
                            String ans = JOptionPane.showInputDialog("Enter the length:");
                            String ans2 = JOptionPane.showInputDialog("Enter the width:");
                            try {
                                rc = Double.parseDouble(ans);
                                rc2 = Double.parseDouble(ans2);
                            } catch (Exception e3) {
                                JOptionPane.showMessageDialog(null, "You should enter a num. Try again", "ERROR!",
                                        JOptionPane.ERROR_MESSAGE);
                                CC = 0;
                            }
                            if (rc != -1 && rc2 != -1) {
                                Map<String, Double> pb = new HashMap<String, Double>();
                                pb.put("width", rc);
                                pb.put("height", rc2);
                                tmp.setProperties(pb);
                                updateShape(memory.elementAt(i), tmp);
                            }
                        } else if (s == 'L') {
                            String ans = JOptionPane.showInputDialog("Enter the length:");
                            try {
                                rc = Double.parseDouble(ans);
                            } catch (Exception e3) {
                                JOptionPane.showMessageDialog(null, "You should enter a num. Try again", "ERROR!",
                                        JOptionPane.ERROR_MESSAGE);
                                CC = 0;
                            }
                            if (rc != -1) {
                                Map<String, Double> pb = new HashMap<String, Double>();
                                pb.put("length", rc);
                                tmp.setProperties(pb);
                                updateShape(memory.elementAt(i), tmp);
                            }
                        }
                        break;
                    }
                }
            }
            CC = 0;
        } else if (CC == 11) { //delete
            char s = sh.charAt(0);
            int c = Integer.parseInt(sh.substring(1));
            if (!Character.isUpperCase(s) || c == 0) {
                JOptionPane.showMessageDialog(null, "Error.", "", JOptionPane.ERROR_MESSAGE);
                CC = 0;
                repaint();
            }
            int j = 0;
            for (int i = 0; i < memory.size(); i++) {
                if (memory.elementAt(i).toString().charAt(26) == s) {
                    j++;
                    if (j == c) {
                        removeShape(memory.elementAt(i));
                        break;
                    }
                }
            }
            CC = 0;
        } else if (CC == 12) { //save_load
            refresh(g2);
        }
    }
 
    public gui() {
        getContentPane().setLayout(null);
        this.getContentPane().setBackground(Color.WHITE);
        this.setTitle("Paint");
        this.setSize(650, 540);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(Color.white);
        this.setResizable(false);
        final int BI_WIDTH = 50;
        final int BI_HEIGHT = 50;
 
        p = new JPanel();
        p.setBounds(120, 0, 526, 512);
        getContentPane().add(p);
        p.setLayout(null);
 
        p.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
 
                x = e.getX() + 125;
                y = e.getY();
                repaint();
            }
        });
 
        BufferedImage lineImage = new BufferedImage(BI_WIDTH, BI_HEIGHT, BufferedImage.TYPE_INT_RGB);
        lineImage.createGraphics().drawOval(11, 13, 25, 25);
        circle = new JButton();
        circle.setIcon(new ImageIcon(lineImage));
        circle.setBackground(Color.black);
        BufferedImage lineImage1 = new BufferedImage(BI_WIDTH, BI_HEIGHT, BufferedImage.TYPE_INT_RGB);
        lineImage1.createGraphics().drawRect(9, 13, 36, 25);
        rectangle = new JButton("");
        rectangle.setIcon(new ImageIcon(lineImage1));
        rectangle.setBackground(Color.black);
 
        BufferedImage lineImage2 = new BufferedImage(BI_WIDTH, BI_HEIGHT, BufferedImage.TYPE_INT_RGB);
        lineImage2.createGraphics().drawRect(9, 13, 25, 25);
        square = new JButton("");
        square.setIcon(new ImageIcon(lineImage2));
        square.setBackground(Color.black);
 
        BufferedImage lineImage3 = new BufferedImage(BI_WIDTH, BI_HEIGHT, BufferedImage.TYPE_INT_RGB);
        lineImage3.createGraphics().drawOval(9, 13, 35, 25);
        ellipse = new JButton("");
        ellipse.setIcon(new ImageIcon(lineImage3));
        ellipse.setBackground(Color.black);
        // =======================================//
        int z[] = { 25, 10, 35 };
        int f[] = { 10, 33, 33 };
 
        BufferedImage lineImage5 = new BufferedImage(BI_WIDTH, BI_HEIGHT, BufferedImage.TYPE_INT_RGB);
        lineImage5.createGraphics().drawPolygon(z, f, 3);
        triangle = new JButton("");
        triangle.setIcon(new ImageIcon(lineImage5));
        triangle.setBackground(Color.black);
        // =======================================//
        BufferedImage lineImage4 = new BufferedImage(BI_WIDTH, BI_HEIGHT, BufferedImage.TYPE_INT_RGB);
        lineImage4.createGraphics().drawLine(8, 15, 35, 35);
        line = new JButton("");
        line.setIcon(new ImageIcon(lineImage4));
        line.setBackground(Color.black);
 
        getContentPane().add(line);
        getContentPane().add(circle);
        getContentPane().add(rectangle);
        getContentPane().add(square);
        getContentPane().add(triangle);
        getContentPane().add(ellipse);
 
        /* rEdo,uNdo */
        uNdo = new JButton("Undo");
        uNdo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        getContentPane().add(uNdo);
        rEdo = new JButton("Redo");
        rEdo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        getContentPane().add(rEdo);
 
        uNdo.setBounds(10, 317, 100, 25);
        rEdo.setBounds(10, 342, 100, 25);
        uNdo.addActionListener(this);
        rEdo.addActionListener(this);
        /* bluecolor,whitecolor,blackcolor,cyancolor,pinkcolor,redcolor */
        bluecolor = new JButton();
        getContentPane().add(bluecolor);
        graycolor = new JButton();
        getContentPane().add(graycolor);
        cyancolor = new JButton();
        getContentPane().add(cyancolor);
        pinkcolor = new JButton();
        getContentPane().add(pinkcolor);
        redcolor = new JButton();
        getContentPane().add(redcolor);
 
        bluecolor.setBounds(10, 190, 40, 40);
        bluecolor.setBackground(Color.blue);
 
        graycolor.setBounds(51, 190, 40, 40);
        graycolor.setBackground(Color.LIGHT_GRAY);
 
        cyancolor.setBounds(10, 276, 40, 40);
        cyancolor.setBackground(Color.cyan);
 
        pinkcolor.setBounds(51, 233, 40, 40);
        pinkcolor.setBackground(Color.pink);
 
        redcolor.setBounds(51, 276, 40, 40);
        redcolor.setBackground(Color.red);
 
        bluecolor.addActionListener(this);
        cyancolor.addActionListener(this);
        graycolor.addActionListener(this);
        pinkcolor.addActionListener(this);
        redcolor.addActionListener(this);
 
        /* move, resize,save,load */
        btnMove = new JButton("Move");
        getContentPane().add(btnMove);
        btnMove.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnMove.setBounds(10, 368, 100, 25);
 
        btnResize = new JButton("Resize");
        getContentPane().add(btnResize);
        btnResize.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnResize.setBounds(10, 393, 100, 25);
 
        btnSave = new JButton("Save");
        getContentPane().add(btnSave);
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBounds(10, 446, 100, 30);
 
        btnLoad = new JButton("Load");
        getContentPane().add(btnLoad);
        btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnLoad.setBounds(10, 472, 100, 30);
 
        circle.setBounds(10, 0, 100, 30);
        square.setBounds(10, 31, 100, 30);
        rectangle.setBounds(10, 62, 100, 30);
        triangle.setBounds(10, 93, 100, 30);
        ellipse.setBounds(10, 124, 100, 30);
        line.setBounds(10, 155, 100, 30);
        blackcolor = new JButton();
        blackcolor.setBounds(10, 233, 40, 40);
        getContentPane().add(blackcolor);
        blackcolor.setBackground(Color.black);
 
        btnDelete = new JButton("Delete");
        getContentPane().add(btnDelete);
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDelete.setBounds(10, 418, 100, 25);
        blackcolor.addActionListener(this);
 
        circle.addActionListener(this);
        rectangle.addActionListener(this);
        triangle.addActionListener(this);
        square.addActionListener(this);
        ellipse.addActionListener(this);
        line.addActionListener(this);
        btnMove.addActionListener(this);
        btnResize.addActionListener(this);
        btnSave.addActionListener(this);
        btnLoad.addActionListener(this);
        btnDelete.addActionListener(this);
 
    }
 
    public static void main(String args[]) throws Exception {
        new gui();
 
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
 
        if (e.getSource() == circle) {
            String ans = JOptionPane.showInputDialog("Enter the radius:");
            if (ans == null || ans.isEmpty()) {
                ans = JOptionPane.showInputDialog(null,
                        "You should enter the radius or it will be drawn with a random size!", "BE AWARE!",
                        JOptionPane.ERROR_MESSAGE);
            }
            try {
                r = Double.parseDouble(ans);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "You Should enter a num! it will be drawn with a random size!");
                ans = "100";
            }
            r = Double.parseDouble(ans);
            COUNT = 1;
        } else if (e.getSource() == rectangle) {
            String ans = JOptionPane.showInputDialog("Enter the length:");
            String ans2 = JOptionPane.showInputDialog("Enter the width:");
            if (ans == null || ans2 == null || ans2.isEmpty() || ans.isEmpty()) {
                JOptionPane.showMessageDialog(null, "It will be drawn with a random size.", "BE AWARE!",
                        JOptionPane.ERROR_MESSAGE);
                ans = "90";
                ans2 = "150";
            }
            try {
                l = Double.parseDouble(ans);
                w = Double.parseDouble(ans2);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "You Should enter a num! it will be drawn with a random size!");
                ans = "90";
                ans2 = "150";
            }
            l = Double.parseDouble(ans);
            w = Double.parseDouble(ans2);
            COUNT = 2;
 
        } else if (e.getSource() == square) {
            String ans = JOptionPane.showInputDialog("Enter the sidelength:");
            if (ans == null || ans.isEmpty()) {
                ans = JOptionPane.showInputDialog(null,
                        "You should enter the sidelength or it will be drawn with a random length!", "BE AWARE!",
                        JOptionPane.ERROR_MESSAGE);
            }
            try {
                ls = Double.parseDouble(ans);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "You Should enter a num! it will be drawn with a random size!");
                ans = "100";
            }
            ls = Double.parseDouble(ans);
            COUNT = 3;
 
        } else if (e.getSource() == ellipse) {
            String ans = JOptionPane.showInputDialog("Enter the radius1:");
 
            String ans1 = JOptionPane.showInputDialog("Enter the radius2:");
 
            if (ans == null || ans1 == null || ans1.isEmpty() || ans.isEmpty()) {
                JOptionPane.showMessageDialog(null, "It will be drawn with a random size.", "BE AWARE!",
                        JOptionPane.ERROR_MESSAGE);
                ans = "90";
                ans1 = "150";
            }
            try {
                r1 = Double.parseDouble(ans);
                r2 = Double.parseDouble(ans1);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "You Should enter a num! it will be drawn with a random size!");
                ans = "90";
                ans1 = "150";
            }
            r1 = Double.parseDouble(ans);
            r2 = Double.parseDouble(ans1);
            COUNT = 4;
 
        } else if (e.getSource() == triangle) {
            String ans2 = JOptionPane.showInputDialog("Enter the sidelength:");
            if (ans2 == null || ans2.isEmpty()) {
                ans2 = JOptionPane.showInputDialog(null,
                        "You should enter the sidelength or it will be drawn with a random length!", "BE AWARE!",
                        JOptionPane.ERROR_MESSAGE);
            }
            try {
                tl = Double.parseDouble(ans2);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "You Should enter a num! it will be drawn with a random size!");
                ans2 = "100";
            }
            tl = Double.parseDouble(ans2);
            COUNT = 5;
 
        } else if (e.getSource() == line) {
            String ans = JOptionPane.showInputDialog("Enter the length:");
            if (ans == null || ans.isEmpty()) {
                ans = JOptionPane.showInputDialog(null,
                        "You should enter the length or it will be drawn with a random length!", "BE AWARE!",
                        JOptionPane.ERROR_MESSAGE);
            }
            try {
                ll = Double.parseDouble(ans);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "You Should enter a num! it will be drawn with a random size!");
                ans = "100";
            }
            ll = Double.parseDouble(ans);
            COUNT = 6;
 
        } else if (e.getSource() == blackcolor) {
            if (memory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose a shape first!");
                CC = 0;
            } else {
                CC = 1;
                repaint();
            }
        } else if (e.getSource() == bluecolor) {
            if (memory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose a shape first!");
                CC = 0;
            } else {
                CC = 2;
                repaint();
            }
        } else if (e.getSource() == pinkcolor) {
            if (memory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose a shape first!");
                CC = 0;
            } else {
                CC = 3;
                repaint();
            }
        } else if (e.getSource() == redcolor) {
            if (memory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose a shape first!");
                CC = 0;
            } else {
                CC = 4;
                repaint();
            }
        } else if (e.getSource() == graycolor) {
            if (memory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose a shape first!");
                CC = 0;
            } else {
                CC = 5;
                repaint();
            }
        } else if (e.getSource() == cyancolor) {
            if (memory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose a shape first!");
                CC = 0;
            } else {
                CC = 6;
                repaint();
            }
 
        } else if (e.getSource() == uNdo) {
            CC = 7;
            repaint();
 
        } else if (e.getSource() == rEdo) {
            CC = 8;
            repaint();
 
        } else if (e.getSource() == btnMove) {
            sh = JOptionPane.showInputDialog(null,
                    "Enter The Shape to be moved. \n" + "Hint : C1 for the first Circle you draw.");
            JOptionPane.showMessageDialog(null, "Now click on the wanted position!");
            CC = 9;
 
        } else if (e.getSource() == btnResize) {
            sh = JOptionPane.showInputDialog(null,
                    "Enter The Shape to be Resized. \n" + "Hint : C1 for the first Circle you draw.");
            CC = 10;
            repaint();
        } else if (e.getSource() == btnDelete) {
            sh = JOptionPane.showInputDialog(null,
                    "Enter The Shape to be Deleted. \n" + "Hint : C1 for the first Circle you draw.");
            CC = 11;
            repaint();
        } else if (e.getSource() == btnSave) {
            JFileChooser ch = new JFileChooser();
            ch.showSaveDialog(btnSave);
            File ft = new File(ch.getSelectedFile() + ".xml");
            String p = ft.getAbsolutePath();
            save(p);
        } else if (e.getSource() == btnLoad) {
            JFileChooser ch = new JFileChooser();
            ch.showOpenDialog(btnLoad);
            File ft = new File(ch.getSelectedFile() + "");
            String p = ft.getAbsolutePath();
            load(p);
        }
    }
}
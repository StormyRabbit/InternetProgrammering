import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.*;

public class GUI extends JFrame implements guiObserver {
    private Paper p = new Paper();
    private UDPSender sender;
    private UDPReceiver udpr;
    private Queue<Point> queue;
    
    public static void main(String[] args) {
        new GUI(args);
    }

    public GUI(String[] args) {
        queue = new LinkedList();
        try {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(p, BorderLayout.CENTER);
        setSize(640, 480);
        setVisible(true);
        sender = new UDPSender(args);
        udpr = new UDPReceiver(args, this);
        p.addMouseListeners(new L1(), new L2());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Point point) {
        p.addPoint(point);
    }

    class L1 extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            Point point = me.getPoint();
            p.addPoint(point);
            sender.sendPoint(point);
        }
    }

    class L2 extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent me) {
            Point point = me.getPoint();
            p.addPoint(point);
            sender.sendPoint(point);
        }
    }

}

class Paper extends JPanel {
    private HashSet hs = new HashSet();
    public Paper() {
        setBackground(Color.white);

    }

    public void addMouseListeners(MouseAdapter l1, MouseMotionAdapter l2) {
        addMouseListener(l1);
        addMouseMotionListener(l2);
    }

    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        Iterator i = hs.iterator();
        while(i.hasNext()) {
            Point p = (Point)i.next();
            g.fillOval(p.x, p.y, 2, 2);
        }
    }

    public synchronized void addPoint(Point p) {
        hs.add(p);
        repaint();
    }


}
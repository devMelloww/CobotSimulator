package view;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    private int angle1 = 0;  // Initializing the angles to 0
    private int angle2 = 0;
    private int angle3 = 0;
    private int angle4 = 0;
    private int angle5 = 0;
    private int angle6 = 0;

    public SimulationPanel() {
        // Initialize panel properties if needed
        setPreferredSize(new Dimension(800, 600)); // Set the size of the panel
    }

    // Method to set angles, to be called externally (optional)
    public void setAngles(int angle1, int angle2, int angle3, int angle4, int angle5, int angle6) {
        this.angle1 = angle1;
        this.angle2 = angle2;
        this.angle3 = angle3;
        this.angle4 = angle4;
        this.angle5 = angle5;
        this.angle6 = angle6;
        repaint(); // Repaint the panel to reflect the new angles
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Always call the superclass's method first
        drawArm(g);  // Call the custom drawing method
    }

    // Method to draw the cobot arm based on the current angles
    public void drawArm(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));

        int x1 = 300, y1 = 450;
        int length = 50;

        // Draw each segment based on the angles
        int x2 = x1 + (int) (length * Math.cos(Math.toRadians(angle1)));
        int y2 = y1 - (int) (length * Math.sin(Math.toRadians(angle1)));
        g2d.drawLine(x1, y1, x2, y2);

        int x3 = x2 + (int) (length * Math.cos(Math.toRadians(angle2)));
        int y3 = y2 - (int) (length * Math.sin(Math.toRadians(angle2)));
        g2d.drawLine(x2, y2, x3, y3);

        int x4 = x3 + (int) (length * Math.cos(Math.toRadians(angle3)));
        int y4 = y3 - (int) (length * Math.sin(Math.toRadians(angle3)));
        g2d.drawLine(x3, y3, x4, y4);

        int x5 = x4 + (int) (length * Math.cos(Math.toRadians(angle4)));
        int y5 = y4 - (int) (length * Math.sin(Math.toRadians(angle4)));
        g2d.drawLine(x4, y4, x5, y5);

        int x6 = x5 + (int) (length * Math.cos(Math.toRadians(angle5)));
        int y6 = y5 - (int) (length * Math.sin(Math.toRadians(angle5)));
        g2d.drawLine(x5, y5, x6, y6);

        int x7 = x6 + (int) (length * Math.cos(Math.toRadians(angle6)));
        int y7 = y6 - (int) (length * Math.sin(Math.toRadians(angle6)));
        g2d.drawLine(x6, y6, x7, y7);

        // Draw joints as blue circles
        g2d.setColor(Color.BLUE);
        g2d.fillOval(x1 - 5, y1 - 5, 10, 10); // Joint 1
        g2d.fillOval(x2 - 5, y2 - 5, 10, 10); // Joint 2
        g2d.fillOval(x3 - 5, y3 - 5, 10, 10); // Joint 3
        g2d.fillOval(x4 - 5, y4 - 5, 10, 10); // Joint 4
        g2d.fillOval(x5 - 5, y5 - 5, 10, 10); // Joint 5
        g2d.fillOval(x6 - 5, y6 - 5, 10, 10); // Joint 6
    }
}

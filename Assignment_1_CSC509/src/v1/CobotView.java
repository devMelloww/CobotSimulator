package v1;

import javax.swing.*;
import java.awt.*;

public class CobotView extends JPanel implements Observer {
    private final CobotModel model;
    private int angle1, angle2, angle3, angle4, angle5, angle6;

    public CobotView(CobotModel model) {
        this.model = model;
        model.addObserver(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("Repainting with angles: " + angle1 + ", " + angle2 + ", " + angle3);
        drawArm(g);
    }

    public void setAngles(int angle1, int angle2, int angle3, int angle4, int angle5, int angle6) {
        this.angle1 = angle1;
        this.angle2 = angle2;
        this.angle3 = angle3;
        this.angle4 = angle4;
        this.angle5 = angle5;
        this.angle6 = angle6;
        System.out.println("Setting angles in view: " + angle1 + ", " + angle2 + ", " + angle3);
    }


    private void drawArm(Graphics g) {
        int[] angles = model.getAngles();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));

        int x1 = 300, y1 = 450;
        int length = 50;

        // Draw segments based on angles
        int x2 = x1 + (int) (length * Math.cos(Math.toRadians(angles[0])));
        int y2 = y1 - (int) (length * Math.sin(Math.toRadians(angles[0])));
        g2d.drawLine(x1, y1, x2, y2);

        // Second segment (based on angle2)
        int x3 = x2 + (int) (length * Math.cos(Math.toRadians(angles[1])));
        int y3 = y2 - (int) (length * Math.sin(Math.toRadians(angles[1])));
        g2d.drawLine(x2, y2, x3, y3);

        // Third segment (based on angle3)
        int x4 = x3 + (int) (length * Math.cos(Math.toRadians(angles[2])));
        int y4 = y3 - (int) (length * Math.sin(Math.toRadians(angles[2])));
        g2d.drawLine(x3, y3, x4, y4);

        // Fourth segment (based on angle4)
        int x5 = x4 + (int) (length * Math.cos(Math.toRadians(angles[3])));
        int y5 = y4 - (int) (length * Math.sin(Math.toRadians(angles[3])));
        g2d.drawLine(x4, y4, x5, y5);

        // Fifth segment (based on angle5)
        int x6 = x5 + (int) (length * Math.cos(Math.toRadians(angles[4])));
        int y6 = y5 - (int) (length * Math.sin(Math.toRadians(angles[4])));
        g2d.drawLine(x5, y5, x6, y6);

        // Sixth segment (based on angle6)
        int x7 = x6 + (int) (length * Math.cos(Math.toRadians(angles[5])));
        int y7 = y6 - (int) (length * Math.sin(Math.toRadians(angles[5])));
        g2d.drawLine(x6, y6, x7, y7);

        // Draw joints
        g2d.setColor(Color.BLUE);
        g2d.fillOval(x1 - 5, y1 - 5, 10, 10); // Joint 1
        g2d.fillOval(x2 - 5, y2 - 5, 10, 10); // Joint 2
        g2d.fillOval(x3 - 5, y3 - 5, 10, 10); // Joint 3
        g2d.fillOval(x4 - 5, y4 - 5, 10, 10); // Joint 4
        g2d.fillOval(x5 - 5, y5 - 5, 10, 10); // Joint 5
        g2d.fillOval(x6 - 5, y6 - 5, 10, 10); // Joint 6
    }

    @Override
    public void update() {
        repaint();  // Repaint the arm when the model updates
    }
}




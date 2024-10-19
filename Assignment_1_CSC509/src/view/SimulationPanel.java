package view;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SimulationPanel extends JPanel implements PropertyChangeListener {
    private int angle1 = 0;  // Initializing the angles to 0
    private int angle2 = 0;
    private int angle3 = 0;
    private int angle4 = 0;
    private int angle5 = 0;
    private int angle6 = 0;
    private Queue<int[]> AngleQueue = new LinkedList<>();

    private boolean Simulate = false;

    public SimulationPanel() {
        // Initialize panel properties if needed
        setPreferredSize(new Dimension(800, 600)); // Set the size of the panel
    }

    public void StartSimulation() {
        Simulate = true;
        RunSimulation();
    }

    public void StopSimulation() {
        Simulate = false;
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

    private void RunSimulation() {
        while (!AngleQueue.isEmpty() && Simulate) {
            int[] angles = AngleQueue.remove();
            setAngles(angles[0], angles[1], angles[2], angles[3], angles[4], angles[5]);
            String angleStr = String.format("[%d, %d, %d, %d, %d, %d]", angles[0],
                                            angles[1], angles[2], angles[3], angles[4],
                                            angles[5]);
            int result = JOptionPane.showConfirmDialog(null,
                                              "Angles\n" + angleStr + "\nset!\nContinue?");

            if (result != 0) { //yes == 0, no/cancel != 0
                break;
            }
        }

        if (AngleQueue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All angles simulated!");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int[] angles = (int[]) evt.getNewValue();
        AngleQueue.add(angles);
        System.out.println("Simulator received angles from BlackBoard");
        displayAngles();
    }

    public void displayAngles() {
        System.out.println("Angles Queued to be simulated:");

        for (int[] anglesArray : AngleQueue) {
            System.out.print("[");
            for (int i = 0; i < anglesArray.length; i++) {
                System.out.print(anglesArray[i]);
                if (i < anglesArray.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
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

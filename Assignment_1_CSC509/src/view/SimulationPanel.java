package view;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.Queue;

public class SimulationPanel extends JPanel implements PropertyChangeListener {
    private int angle1 = 0, angle2 = 0, angle3 = 0, angle4 = 0, angle5 = 0, angle6 = 0;
    private int targetAngle1 = 0, targetAngle2 = 0, targetAngle3 = 0, targetAngle4 = 0, targetAngle5 = 0, targetAngle6 = 0;
    private Queue<int[]> AngleQueue = new LinkedList<>();
    private Timer simulationTimer;
    private boolean simulate = false;
    private int phase = 1;  // Track which angle is currently being animated

    public SimulationPanel() {
        setPreferredSize(new Dimension(800, 600)); // Set the size of the panel
    }


    public void StartSimulation() {
        if (AngleQueue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No angles to simulate.");
            return;
        }
        simulate = true;
        phase = 1; // Start with the first angle
        RunSimulation();
    }

    public void StopSimulation() {
        if (simulationTimer != null && simulationTimer.isRunning()) {
            simulationTimer.stop();
        }
        simulate = false;
    }

    private void RunSimulation() {
        if (AngleQueue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All angles simulated.");
            return;
        }

        // Get the next set of angles from the queue
        int[] angles = AngleQueue.remove();

        // Add the new angles to the current targets (cumulative rotation)
        targetAngle1 += angles[0];
        targetAngle2 += angles[1];
        targetAngle3 += angles[2];
        targetAngle4 += angles[3];
        targetAngle5 += angles[4];
        targetAngle6 += angles[5];

        final int delay = 50; // Delay for each update in milliseconds
        simulationTimer = new Timer(delay, e -> updateAngles());
        simulationTimer.start(); // Start the timer to begin simulation
    }

    // Method to update angles incrementally and move to the next phase
    private void updateAngles() {
        boolean finished = false;

        switch (phase) {
            case 1:
                if (adjustAngleTowardsTarget(1, targetAngle1)) {
                    phase = 2;
                }
                break;
            case 2:
                if (adjustAngleTowardsTarget(2, targetAngle2)) {
                    phase = 3;
                }
                break;
            case 3:
                if (adjustAngleTowardsTarget(3, targetAngle3)) {
                    phase = 4;
                }
                break;
            case 4:
                if (adjustAngleTowardsTarget(4, targetAngle4)) {
                    phase = 5;
                }
                break;
            case 5:
                if (adjustAngleTowardsTarget(5, targetAngle5)) {
                    phase = 6;
                }
                break;
            case 6:
                if (adjustAngleTowardsTarget(6, targetAngle6)) {
                    finished = true; // All angles for this set are done
                }
                break;
        }

        repaint(); // Redraw the arm based on updated angles

        if (finished) {
            simulationTimer.stop(); // Stop the timer for this set

            // Check if there are more sets of angles to simulate
            if (!AngleQueue.isEmpty()) {
                phase = 1; // Reset phase for the next set of angles
                RunSimulation(); // Automatically start simulating the next set
            } else {
                JOptionPane.showMessageDialog(null, "All angle sets have been simulated!");
            }
        }
    }

    // Adjust the specific angle towards its target (incrementally towards cumulative targets)
    private boolean adjustAngleTowardsTarget(int angleIndex, int targetAngle) {
        switch (angleIndex) {
            case 1:
                if (angle1 < targetAngle) {
                    angle1++;
                } else if (angle1 > targetAngle) {
                    angle1--;
                } else {
                    return true; // Finished with this angle
                }
                break;
            case 2:
                if (angle2 < targetAngle) {
                    angle2++;
                } else if (angle2 > targetAngle) {
                    angle2--;
                } else {
                    return true;
                }
                break;
            case 3:
                if (angle3 < targetAngle) {
                    angle3++;
                } else if (angle3 > targetAngle) {
                    angle3--;
                } else {
                    return true;
                }
                break;
            case 4:
                if (angle4 < targetAngle) {
                    angle4++;
                } else if (angle4 > targetAngle) {
                    angle4--;
                } else {
                    return true;
                }
                break;
            case 5:
                if (angle5 < targetAngle) {
                    angle5++;
                } else if (angle5 > targetAngle) {
                    angle5--;
                } else {
                    return true;
                }
                break;
            case 6:
                if (angle6 < targetAngle) {
                    angle6++;
                } else if (angle6 > targetAngle) {
                    angle6--;
                } else {
                    return true;
                }
                break;
        }
        return false; // Still adjusting the angle
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int[] angles = (int[]) evt.getNewValue();
        AngleQueue.add(angles);
        System.out.println("Simulator received angles from Blackboard");

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawArm(g);  // Call the custom drawing method
    }

    public void drawArm(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));

        int x1 = 300, y1 = 450;
        int length = 50;

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
        g2d.fillOval(x7 - 5, y7 - 5, 10, 10); // End Effector
    }
}

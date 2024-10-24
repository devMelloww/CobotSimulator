package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.Queue;

/***
 * This class represents a graphical panel that visualizes the simulation of robotic arm movements.
 * It listens for changes in angles from the Blackboard and updates the GUI.
 */
public class SimulationPanel extends JPanel implements PropertyChangeListener {
    private int angle1 = 0, angle2 = 0, angle3 = 0, angle4 = 0, angle5 = 0, angle6 = 0;
    private Queue<int[]> AngleQueue = new LinkedList<>();
    private JLabel staticStatusLabel;
    private JLabel dynamicStatusLabel;
    private boolean isRunning = false;

    /***
     * Constructor to create the SimulationPanel and set its preferred size.
     */
    public SimulationPanel() {
        setPreferredSize(new Dimension(800, 600));

        staticStatusLabel = new JLabel("Status: ");
        staticStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        staticStatusLabel.setForeground(Color.BLACK);  // Always black

        dynamicStatusLabel = new JLabel("Idle");
        dynamicStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dynamicStatusLabel.setForeground(Color.RED);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(staticStatusLabel);
        this.add(dynamicStatusLabel);
    }

    /***
     * Getter methods to retrieve the current angles.
     * @return current angles.
     */
    public int getAngle1() {
        return angle1;
    }
    public int getAngle2() {
        return angle2;
    }
    public int getAngle3() {
        return angle3;
    }
    public int getAngle4() {
        return angle4;
    }
    public int getAngle5() {
        return angle5;
    }
    public int getAngle6() {
        return angle6;
    }

    /***
     * Increment methods to update angles.
     */
    public void incrementAngle1() {
        angle1++;
    }
    public void incrementAngle2() {
        angle2++;
    }
    public void incrementAngle3() {
        angle3++;
    }
    public void incrementAngle4() {
        angle4++;
    }
    public void incrementAngle5() {
        angle5++;
    }
    public void incrementAngle6() {
        angle6++;
    }

    /***
     * Decrement methods to update angles.
     */
    public void decrementAngle1() {
        angle1--;
    }
    public void decrementAngle2() {
        angle2--;
    }
    public void decrementAngle3() {
        angle3--;
    }
    public void decrementAngle4() {
        angle4--;
    }
    public void decrementAngle5() {
        angle5--;
    }
    public void decrementAngle6() {
        angle6--;
    }

    /***
     * Getter for the angle queue
     * @return
     */
    public Queue<int[]> getAngleQueue() {
        return AngleQueue;
    }

    /***
     * Resets all angles to initial values (0 degrees).
     */
    public void resetAngles() {
        angle1 = 0;
        angle2 = 0;
        angle3 = 0;
        angle4 = 0;
        angle5 = 0;
        angle6 = 0;
        repaint(); // Repaint to reflect the reset state
    }

    /***
     * Method to set the status to "Running" when the simulation starts.
     */
    public void setRunningStatus() {
        isRunning = true;
        dynamicStatusLabel.setText("Running");
        dynamicStatusLabel.setForeground(Color.decode("#008000"));  // Change to green when running
    }

    /***
     * Method to set the status to "Idle" when the simulation stops.
     */
    public void setIdleStatus() {
        isRunning = false;
        dynamicStatusLabel.setText("Idle");
        dynamicStatusLabel.setForeground(Color.RED);  // Change to red when idle
    }

    /***
     * This method receives new angles from the Blackboard and adds them to the queue.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int[] angles = (int[]) evt.getNewValue();
        AngleQueue.add(angles);

        System.out.println("Simulator received angles from Blackboard");
    }

    /***
     * This method is called by the Swing framework whenever the panel needs to be repainted.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawArm(g);
    }

    /***
     * This method draws the robotic arm on the panel based on current angles.
     * @param g Used for drawing
     */
    public void drawArm(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));

       // int x1 = 300, y1 = 450;
        int centerX = (getWidth() / 2) - 150;
        int centerY = getHeight() - 300;
        int length = 60;


        int[] angles = {angle1, angle2, angle3, angle4, angle5, angle6};

        Color[] colors = {
                Color.decode("#6667ab"), // For angle1
                Color.decode("#f18aad"), // For angle2
                Color.decode("#ea6759"), // For angle3
                Color.decode("#f88f58"), // For angle4
                Color.decode("#f3c65f"), // For angle5
                Color.decode("#8bc28c")  // For angle6
        };

        int[] xPositions = new int[angles.length + 1];
        int[] yPositions = new int[angles.length + 1];

        xPositions[0] = centerX;
        yPositions[0] = centerY;

        int x2 = centerX, y2 = centerY;
        for (int i = 0; i < angles.length; i++) {
            int x3 = x2 + (int) (length * Math.cos(Math.toRadians(angles[i])));
            int y3 = y2 - (int) (length * Math.sin(Math.toRadians(angles[i])));

            // Set color and draw line
            g2d.setColor(colors[i]);
            g2d.drawLine(x2, y2, x3, y3);

            xPositions[i + 1] = x3;
            yPositions[i + 1] = y3;

            x2 = x3;
            y2 = y3;
        }

        drawJoints(g2d, xPositions, yPositions);
    }

    /***
     * Draws the joints (as filled ovals) for the robotic arm segments.
     * @param g2d Graphics2D object for drawing.
     * @param xPositions Array of x coordinates for the joints.
     * @param yPositions Array of y coordinates for the joints.
     */
    private void drawJoints(Graphics2D g2d, int[] xPositions, int[] yPositions) {
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < xPositions.length; i++) {
            g2d.fillOval(xPositions[i] - 5, yPositions[i] - 5, 10, 10);
        }
    }

}

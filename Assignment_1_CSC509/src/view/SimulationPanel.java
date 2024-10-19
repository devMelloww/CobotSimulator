package view;

import javax.swing.*;
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

    /***
     * Constructor to create the SimulationPanel and set its preferred size.
     */
    public SimulationPanel() {
        setPreferredSize(new Dimension(800, 600)); // Set the size of the panel
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

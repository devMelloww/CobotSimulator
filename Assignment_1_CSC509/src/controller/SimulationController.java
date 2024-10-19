package controller;

import view.SimulationPanel;
import javax.swing.*;

/***
 * This class handles the controls for simulation of the Cobot
 *
 */
public class SimulationController {
    SimulationPanel simulationPanel;
    private Timer simulationTimer;
    private boolean simulate = false;
    private int targetAngle1 = 0, targetAngle2 = 0, targetAngle3 = 0, targetAngle4 = 0, targetAngle5 = 0, targetAngle6 = 0;
    private int phase = 1;


    /***
     * Constructor setting the simulation panel
     * @param simulationPanel
     */
    public SimulationController(SimulationPanel simulationPanel) {
        this.simulationPanel = simulationPanel;
    }

    /***
     * This method starts the simulation as long as the queue is not empty
     */
    public void StartSimulation() {
        if (simulationPanel.getAngleQueue().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No angles to simulate.");
            return;
        }
        simulate = true;
        phase = 1;
        RunSimulation();
    }

    /***
     * This method stops the simulation and terminates the program
     */
    public void StopSimulation() {
        if (simulationTimer != null && simulationTimer.isRunning()) {
            simulationTimer.stop();
        }
        simulate = false;
        System.exit(0);
    }

    /***
     * This method runs the simulation and adds new angles to current position
     */
    private void RunSimulation() {
        if (simulationPanel.getAngleQueue().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All angles simulated.");
            return;
        }

        int[] angles = simulationPanel.getAngleQueue().remove();

        // Adding new angles to the current targets
        targetAngle1 += angles[0];
        targetAngle2 += angles[1];
        targetAngle3 += angles[2];
        targetAngle4 += angles[3];
        targetAngle5 += angles[4];
        targetAngle6 += angles[5];

        final int delay = 50;
        simulationTimer = new Timer(delay, e -> updateAngles());
        simulationTimer.start();
    }

    /***
     *  Updating angles incrementally to simulate the movement
     */
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

        simulationPanel.repaint();

        if (finished) {
            simulationTimer.stop();

            // Checking if there are more sets of angles to animate
            if (!simulationPanel.getAngleQueue().isEmpty()) {
                phase = 1;
                RunSimulation();
            } else {
                JOptionPane.showMessageDialog(null, "All angle sets have been simulated!");
            }
        }
    }

    /***
     * This method is used to adjust the current angle towards its target
     * @param angleIndex angleIndex the index of the angle to adjust
     * @param targetAngle argetAngle the target angle to adjust towards
     * @return true if the current angle has reached the target angle, false otherwise
     */
    private boolean adjustAngleTowardsTarget(int angleIndex, int targetAngle) {
        switch (angleIndex) {
            case 1:
                if (simulationPanel.getAngle1() < targetAngle) {
                    simulationPanel.incrementAngle1();
                } else if (simulationPanel.getAngle1() > targetAngle) {
                    simulationPanel.decrementAngle1();
                } else {
                    return true;
                }
                break;
            case 2:
                if (simulationPanel.getAngle2() < targetAngle) {
                    simulationPanel.incrementAngle2();
                } else if (simulationPanel.getAngle2() > targetAngle) {
                    simulationPanel.decrementAngle2();
                } else {
                    return true;
                }
                break;
            case 3:
                if (simulationPanel.getAngle3() < targetAngle) {
                    simulationPanel.incrementAngle3();
                } else if (simulationPanel.getAngle3() > targetAngle) {
                    simulationPanel.decrementAngle3();
                } else {
                    return true;
                }
                break;
            case 4:
                if (simulationPanel.getAngle4() < targetAngle) {
                    simulationPanel.incrementAngle4();
                } else if (simulationPanel.getAngle4() > targetAngle) {
                    simulationPanel.decrementAngle4();
                } else {
                    return true;
                }
                break;
            case 5:
                if (simulationPanel.getAngle5() < targetAngle) {
                    simulationPanel.incrementAngle5();
                } else if (simulationPanel.getAngle5() > targetAngle) {
                    simulationPanel.decrementAngle5();
                } else {
                    return true;
                }
                break;
            case 6:
                if (simulationPanel.getAngle6() < targetAngle) {
                    simulationPanel.incrementAngle6();
                } else if (simulationPanel.getAngle6() > targetAngle) {
                   simulationPanel.decrementAngle6();
                } else {
                    return true;
                }
                break;
        }
        return false;
    }

}

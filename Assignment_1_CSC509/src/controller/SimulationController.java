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
    private boolean paused = false;  // Track if simulation is paused


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
        simulationPanel.resetAngles();
        simulationPanel.setIdleStatus();

        targetAngle1 = 0;
        targetAngle2 = 0;
        targetAngle3 = 0;
        targetAngle4 = 0;
        targetAngle5 = 0;
        targetAngle6 = 0;

        JOptionPane.showMessageDialog(null, "Simulation stopped!");
    }

    /***
     * Pause the simulation
     */
    public void PauseSimulation() {
        if (simulate && !paused) {
            paused = true;
            simulationTimer.stop();
            simulationPanel.setIdleStatus();
        }
    }

    /***
     * Resume the simulation
     */
    public void ResumeSimulation() {
        if (simulate && paused) {
            paused = false;
            simulationTimer.start();
            simulationPanel.setRunningStatus();
        }
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

        String angleStr = String.format("[%d, %d, %d, %d, %d, %d]", angles[0],
                angles[1], angles[2], angles[3], angles[4],
                angles[5]);
        int result = JOptionPane.showConfirmDialog(null,
                "Angles\n" + angleStr + "\nset!\nContinue?");

        if (result != 0) { //yes == 0, no/cancel != 0
            System.exit(0);
        }

        final int delay = 50;
        simulationTimer = new Timer(delay, e -> updateAngles());
        simulationTimer.start();
        simulationPanel.setRunningStatus();
    }

    /***
     *  Updating angles incrementally to simulate the movement
     */
    private void updateAngles() {
        if (paused) return;  // Skip updating if paused
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
                simulationPanel.setIdleStatus();
            }
        }
    }

    /***
     * This method is used to adjust the current angle towards its target
     * @param angleIndex angleIndex the index of the angle to adjust
     * @param targetAngle targetAngle the target angle to adjust towards
     * @return true if the current angle has reached the target angle, false otherwise
     */
    private boolean adjustAngleTowardsTarget(int angleIndex, int targetAngle) {
        int[] currentAngles = {
                simulationPanel.getAngle1(),
                simulationPanel.getAngle2(),
                simulationPanel.getAngle3(),
                simulationPanel.getAngle4(),
                simulationPanel.getAngle5(),
                simulationPanel.getAngle6()
        };

        Runnable[] incrementAngles = {
                simulationPanel::incrementAngle1,
                simulationPanel::incrementAngle2,
                simulationPanel::incrementAngle3,
                simulationPanel::incrementAngle4,
                simulationPanel::incrementAngle5,
                simulationPanel::incrementAngle6
        };

        Runnable[] decrementAngles = {
                simulationPanel::decrementAngle1,
                simulationPanel::decrementAngle2,
                simulationPanel::decrementAngle3,
                simulationPanel::decrementAngle4,
                simulationPanel::decrementAngle5,
                simulationPanel::decrementAngle6
        };

        // Adjust angle based on comparison with target
        if (currentAngles[angleIndex - 1] < targetAngle) {
            incrementAngles[angleIndex - 1].run();
        } else if (currentAngles[angleIndex - 1] > targetAngle) {
            decrementAngles[angleIndex - 1].run();
        } else {
            return true; // Angle has reached the target
        }
        return false;
    }

}

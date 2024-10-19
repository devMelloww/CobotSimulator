package controller;

import view.ButtonPanel;
import view.SimulationPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController {
    private ButtonPanel buttonPanel;
    SimulationPanel simulationPanel;
    private Timer simulationTimer;
    private boolean simulate = false;
    private int targetAngle1 = 0, targetAngle2 = 0, targetAngle3 = 0, targetAngle4 = 0, targetAngle5 = 0, targetAngle6 = 0;
    private int phase = 1;

    public SimulationController(SimulationPanel simulationPanel) {
        this.simulationPanel = simulationPanel;
    }

    public void StartSimulation() {
        if (simulationPanel.getAngleQueue().isEmpty()) {
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
        System.exit(0);
    }

    private void RunSimulation() {
        if (simulationPanel.getAngleQueue().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All angles simulated.");
            return;
        }

        // Get the next set of angles from the queue
        int[] angles = simulationPanel.getAngleQueue().remove();

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

        simulationPanel.repaint(); // Redraw the arm based on updated angles

        if (finished) {
            simulationTimer.stop(); // Stop the timer for this set

            // Check if there are more sets of angles to simulate
            if (!simulationPanel.getAngleQueue().isEmpty()) {
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
                if (simulationPanel.getAngle1() < targetAngle) {
                    simulationPanel.incrementAngle1();
                } else if (simulationPanel.getAngle1() > targetAngle) {
                    simulationPanel.decrementAngle1();
                } else {
                    return true; // Finished with this angle
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
        return false; // Still adjusting the angle
    }

}

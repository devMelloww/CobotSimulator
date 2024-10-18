package v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CobotController {
    private final CobotModel model;
    private final CobotView view;
    private Timer simulationTimer;
    private boolean anglesReceived = false;

    private int angle1, angle2, angle3, angle4, angle5, angle6;

    public CobotController(CobotModel model, CobotView view) {
        this.model = model;
        this.view = view;

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startSimulation(); // Start the simulation when the button is pressed
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (simulationTimer != null) {
                    simulationTimer.stop(); // Stop the simulation
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        JFrame frame = new JFrame("Cobot Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }

    private void startSimulation() {
        // Retrieve angles from singleton
        CobotAnglesSingleton anglesSingleton = CobotAnglesSingleton.getInstance();

        if (!anglesSingleton.areAnglesReceived()) {
            JOptionPane.showMessageDialog(null, "No angles received yet.");
            return;
        }

        // Initialize current angles from singleton
        int[] targetAngles = anglesSingleton.getTargetAngles();
        angle1 = targetAngles[0];
        angle2 = targetAngles[1];
        angle3 = targetAngles[2];
        angle4 = targetAngles[3];
        angle5 = targetAngles[4];
        angle6 = targetAngles[5];

        // Set these angles in the view so the view updates properly
        view.setAngles(angle1, angle2, angle3, angle4, angle5, angle6);

        final int delay = 50; // Delay for each update in milliseconds

        simulationTimer = new Timer(delay, new ActionListener() {
            private int phase = 1; // Start with angle1 and move to the next

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean finished = false;

                switch (phase) {
                    case 1:
                        if (angle1 < targetAngles[0]) {
                            angle1++;
                        } else if (angle1 > targetAngles[0]) {
                            angle1--;
                        } else {
                            phase++;
                        }
                        break;

                    case 2:
                        if (angle2 < targetAngles[1]) {
                            angle2++;
                        } else if (angle2 > targetAngles[1]) {
                            angle2--;
                        } else {
                            phase++;
                        }
                        break;

                    case 3:
                        if (angle3 < targetAngles[2]) {
                            angle3++;
                        } else if (angle3 > targetAngles[2]) {
                            angle3--;
                        } else {
                            phase++;
                        }
                        break;

                    case 4:
                        if (angle4 < targetAngles[3]) {
                            angle4++;
                        } else if (angle4 > targetAngles[3]) {
                            angle4--;
                        } else {
                            phase++;
                        }
                        break;

                    case 5:
                        if (angle5 < targetAngles[4]) {
                            angle5++;
                        } else if (angle5 > targetAngles[4]) {
                            angle5--;
                        } else {
                            phase++;
                        }
                        break;

                    case 6:
                        if (angle6 < targetAngles[5]) {
                            angle6++;
                        } else if (angle6 > targetAngles[5]) {
                            angle6--;
                        } else {
                            finished = true; // All angles reached their targets
                        }
                        break;
                }

                // Update the angles in the view
                view.setAngles(angle1, angle2, angle3, angle4, angle5, angle6);
                view.repaint(); // Redraw the view with updated angles

                if (finished) {
                    simulationTimer.stop(); // Stop when all angles are finished
                }
            }
        });

        simulationTimer.start(); // Start the simulation timer
    }
}

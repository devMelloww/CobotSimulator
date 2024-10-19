package view;

import controller.SimulationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * This class creates a panel with control buttons for the simulation. It has a start
 * button to initiate the simulation and a stop button to terminate it.
 */
public class ButtonPanel extends JPanel {
    private JButton startButton;
    private JButton stopButton;

    /***
     * Constructor to create the ButtonPanel and initialize buttons. Sets up action listeners for the
     * start and stop buttons.
     * @param simulationController Responsible for managing simulation actions.
     */
    public ButtonPanel(SimulationController simulationController) {
        startButton = new JButton("Start");
        stopButton = new JButton("Terminate");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulationController.StartSimulation();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulationController.StopSimulation();
            }
        });

        setLayout(new GridLayout(1,2));
        setBackground(Color.GRAY);

        add(startButton);
        add(stopButton);
    }

    /***
     * Getter method for the start button.
     * @return Start button.
     */
    public JButton getStartButton() {
        return startButton;
    }

    /***
     * Getter method for the stop button.
     * @return Stop button.
     */
    public JButton getStopButton() {
        return stopButton;
    }
}

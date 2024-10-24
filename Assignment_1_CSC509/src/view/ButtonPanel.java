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
    private JButton pauseButton;
    private JButton resumeButton;

    /***
     * Constructor to create the ButtonPanel and initialize buttons. Sets up action listeners for the
     * start and stop buttons.
     * @param simulationController Responsible for managing simulation actions.
     */
    public ButtonPanel(SimulationController simulationController) {
        startButton = new JButton("Start");
        stopButton = new JButton("Terminate");
        pauseButton = new JButton("Pause");
        resumeButton = new JButton("Resume");

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

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationController.PauseSimulation();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationController.ResumeSimulation();
            }
        });

        setLayout(new GridLayout(1,4));
        setBackground(Color.GRAY);

        add(startButton);
        add(stopButton);
        add(pauseButton);
        add(resumeButton);
    }
}

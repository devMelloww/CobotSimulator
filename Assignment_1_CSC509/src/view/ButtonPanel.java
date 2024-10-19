package view;

import model.Blackboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private JButton startButton;
    private JButton stopButton;

    public ButtonPanel(SimulationPanel simulationPanel) {
        startButton = new JButton("Start");
        stopButton = new JButton("Terminate");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulationPanel.StartSimulation();
            }
        });

        // Stop button action listener (stops the timer)
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulationPanel.StopSimulation();
            }
        });

        setLayout(new GridLayout(1,2));
        setBackground(Color.GRAY);

        add(startButton);
        add(stopButton);
    }

    // Getter methods for the buttons
    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }
}

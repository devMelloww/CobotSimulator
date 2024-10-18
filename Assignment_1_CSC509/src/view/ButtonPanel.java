package view;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    private JButton startButton;
    private JButton stopButton;

    public ButtonPanel() {
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

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

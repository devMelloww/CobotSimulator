package controller;

import view.ButtonPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController implements ActionListener {
    private ButtonPanel buttonPanel;

    public SimulationController(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
        buttonPanel.getStartButton().addActionListener(this);
        buttonPanel.getStopButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonPanel.getStartButton()) {

        }
        else if (e.getSource() == buttonPanel.getStopButton()) {

        }
    }
}

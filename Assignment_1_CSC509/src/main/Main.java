package main;

import controller.SimulationController;
import model.Blackboard;
import view.ButtonPanel;
import view.SimulationPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Cobot Simulator");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    public static void main(String[] args) {
        // Initialize and display the GUI
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            Blackboard blackboard = new Blackboard();
            ButtonPanel buttonPanel = new ButtonPanel();
            SimulationPanel simulationPanel = new SimulationPanel();
            SimulationController controller = new SimulationController(buttonPanel);

            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.add(simulationPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });

        // Create a blackboard instance to share between server and controller
        Blackboard blackboard = new Blackboard();

        // Start the server and client in separate threads
        Thread serverThread = new Thread(new Server(blackboard));
        Thread clientThread = new Thread(new Client());

        serverThread.start();
        clientThread.start();
    }
}

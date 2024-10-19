package main;

import controller.SimulationController;
import model.Blackboard;
import view.ButtonPanel;
import view.SimulationPanel;

import javax.swing.*;
import java.awt.*;

/***
 * This is the main class that runs the Cobot simulation application. It extends JFrame to create a GUI.
 */
public class Main extends JFrame {

    /***
     * Constructor to initialize the main frame of the Cobot Simulator.
     */
    public Main() {
        setTitle("Cobot Simulator");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    /***
     * The main method initializes and displays the GUI for the Cobot Simulator. It also creates and
     * starts the server and client threads to handle simulation communication.
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            SimulationPanel simulationPanel = new SimulationPanel();
            SimulationController controller = new SimulationController(simulationPanel);
            Blackboard.Instance().AddListener(simulationPanel);
            ButtonPanel buttonPanel = new ButtonPanel(controller);

            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.add(simulationPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });

        Blackboard blackboard = Blackboard.Instance();

        Thread serverThread = new Thread(new Server(blackboard));
        Thread clientThread = new Thread(new Client());

        serverThread.start();
        clientThread.start();
    }
}

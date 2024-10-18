import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class CobotSimulator extends JFrame {
    private JPanel armPanel;
    private int angle1 = 0, angle2 = 0, angle3 = 0, angle4 = 0, angle5 = 0, angle6 = 0;
    private int targetAngle1, targetAngle2, targetAngle3, targetAngle4, targetAngle5, targetAngle6;
    private boolean anglesReceived = false;  // Flag to track if angles have been received
    private Timer simulationTimer;

    public CobotSimulator() {
        // Basic UI setup
        setTitle("Cobot Simulator");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Arm panel to draw the arm
        armPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawArm(g);
            }
        };

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

        // Start button action listener
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (anglesReceived) {  // Only start the simulation if angles have been received
                    startSimulation();
                } else {
                    JOptionPane.showMessageDialog(null, "No angles received yet.");
                }
            }
        });

        // Stop button action listener (stops the timer)
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (simulationTimer != null && simulationTimer.isRunning()) {
                    simulationTimer.stop();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        add(buttonPanel, BorderLayout.WEST);
        add(armPanel, BorderLayout.CENTER);

        // Start the server in a separate thread
        new Thread(this::startServer).start();
    }

    // Function to draw the robotic arm based on angles
    private void drawArm(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));

        int x1 = 300, y1 = 450;
        int length = 50;

        // First segment (based on angle1)
        int x2 = x1 + (int) (length * Math.cos(Math.toRadians(angle1)));
        int y2 = y1 - (int) (length * Math.sin(Math.toRadians(angle1)));
        g2d.drawLine(x1, y1, x2, y2);

        // Second segment (based on angle2)
        int x3 = x2 + (int) (length * Math.cos(Math.toRadians(angle2)));
        int y3 = y2 - (int) (length * Math.sin(Math.toRadians(angle2)));
        g2d.drawLine(x2, y2, x3, y3);

        // Third segment (based on angle3)
        int x4 = x3 + (int) (length * Math.cos(Math.toRadians(angle3)));
        int y4 = y3 - (int) (length * Math.sin(Math.toRadians(angle3)));
        g2d.drawLine(x3, y3, x4, y4);

        // Fourth segment (based on angle4)
        int x5 = x4 + (int) (length * Math.cos(Math.toRadians(angle4)));
        int y5 = y4 - (int) (length * Math.sin(Math.toRadians(angle4)));
        g2d.drawLine(x4, y4, x5, y5);

        // Fifth segment (based on angle5)
        int x6 = x5 + (int) (length * Math.cos(Math.toRadians(angle5)));
        int y6 = y5 - (int) (length * Math.sin(Math.toRadians(angle5)));
        g2d.drawLine(x5, y5, x6, y6);

        // Sixth segment (based on angle6)
        int x7 = x6 + (int) (length * Math.cos(Math.toRadians(angle6)));
        int y7 = y6 - (int) (length * Math.sin(Math.toRadians(angle6)));
        g2d.drawLine(x6, y6, x7, y7);

        // Draw joints
        g2d.setColor(Color.BLUE);
        g2d.fillOval(x1 - 5, y1 - 5, 10, 10); // Joint 1
        g2d.fillOval(x2 - 5, y2 - 5, 10, 10); // Joint 2
        g2d.fillOval(x3 - 5, y3 - 5, 10, 10); // Joint 3
        g2d.fillOval(x4 - 5, y4 - 5, 10, 10); // Joint 4
        g2d.fillOval(x5 - 5, y5 - 5, 10, 10); // Joint 5
        g2d.fillOval(x6 - 5, y6 - 5, 10, 10); // Joint 6
    }

    // Start server to receive angles from client
    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started. Waiting for client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {
                // Read angles from the client
                targetAngle1 = Integer.parseInt(in.readLine());
                targetAngle2 = Integer.parseInt(in.readLine());
                targetAngle3 = Integer.parseInt(in.readLine());
                targetAngle4 = Integer.parseInt(in.readLine());
                targetAngle5 = Integer.parseInt(in.readLine());
                targetAngle6 = Integer.parseInt(in.readLine());

                System.out.println("Angles received: " + targetAngle1 + ", " + targetAngle2 + ", " + targetAngle3 + ", "
                        + targetAngle4 + ", " + targetAngle5 + ", " + targetAngle6);
                JOptionPane.showMessageDialog(null, "Angles received!");

                // Adjust target angles based on current positions
                targetAngle1 += angle1;
                targetAngle2 += angle2;
                targetAngle3 += angle3;
                targetAngle4 += angle4;
                targetAngle5 += angle5;
                targetAngle6 += angle6;

                // Set the flag to indicate that angles have been received
                anglesReceived = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Start simulation by gradually moving the arm, one angle at a time
    private void startSimulation() {
        if (!anglesReceived){
            JOptionPane.showMessageDialog(null, "No angles received yet.");
        }
        final int delay = 50; // Delay for each update in milliseconds

        simulationTimer = new Timer(delay, new ActionListener() {
            private int phase = 1; // Track which angle is being animated (1 for angle1, 2 for angle2, ..., 6 for angle6)

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean finished = false;

                switch (phase) {
                    case 1: // Animate angle1
                        if (angle1 < targetAngle1) {
                            angle1++;
                        } else if (angle1 > targetAngle1) {
                            angle1--;
                        } else {
                            phase = 2; // Move to the next phase (angle2)
                        }
                        break;

                    case 2: // Animate angle2
                        if (angle2 < targetAngle2) {
                            angle2++;
                        } else if (angle2 > targetAngle2) {
                            angle2--;
                        } else {
                            phase = 3; // Move to the next phase (angle3)
                        }
                        break;

                    case 3: // Animate angle3
                        if (angle3 < targetAngle3) {
                            angle3++;
                        } else if (angle3 > targetAngle3) {
                            angle3--;
                        } else {
                            phase = 4; // Move to the next phase (angle4)
                        }
                        break;

                    case 4: // Animate angle4
                        if (angle4 < targetAngle4) {
                            angle4++;
                        } else if (angle4 > targetAngle4) {
                            angle4--;
                        } else {
                            phase = 5; // Move to the next phase (angle5)
                        }
                        break;

                    case 5: // Animate angle5
                        if (angle5 < targetAngle5) {
                            angle5++;
                        } else if (angle5 > targetAngle5) {
                            angle5--;
                        } else {
                            phase = 6; // Move to the next phase (angle6)
                        }
                        break;

                    case 6: // Animate angle6
                        if (angle6 < targetAngle6) {
                            angle6++;
                        } else if (angle6 > targetAngle6) {
                            angle6--;
                        } else {
                            finished = true; // All angles have reached their target
                        }
                        break;
                }

                armPanel.repaint(); // Redraw the arm based on updated angles

                if (finished) {
                    simulationTimer.stop(); // Stop the simulation if all angles are done
                }
            }
        });

        simulationTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CobotSimulator().setVisible(true);
        });
    }
}

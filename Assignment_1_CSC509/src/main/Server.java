package main;

import model.Blackboard;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * The Server class implements Runnable and is responsible for managing client connections and
 * receiving angles sent from client.
 */
public class Server implements Runnable {
    private Blackboard blackboard;

    /***
     * Constructor to initialize the Server with a given blackboard instance.
     * @param blackboard blackboard The blackboard instance used to store angles.
     */
    public Server(Blackboard blackboard) {
        this.blackboard = blackboard;
    }

    /***
     * This method sets up a server socket to listen for client connections, receives angles
     * from connected clients, and stores the data in the blackboard instance.
     */
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("main.Server started, waiting for clients to connect...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("main.Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            int[] angles = new int[6];
            String receivedData;
            int count = 0;

            while ((receivedData = in.readLine()) != null) {
                try {
                    angles[count] = Integer.parseInt(receivedData);
                    count++;

                    if (count == 6) {
                        blackboard.SetAngles(angles);
                        System.out.println("Angles received and stored in blackboard.");
                        angles = new int[6];
                        count = 0;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid angle data received: " + receivedData);
                }
            }

            clientSocket.close();
            System.out.println("Client disconnected.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package main;

import model.Blackboard;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private Blackboard blackboard;

    public Server(Blackboard blackboard) {
        this.blackboard = blackboard;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("main.Server started, waiting for clients to connect...");

            // Wait for a client to connect
            Socket clientSocket = serverSocket.accept();
            System.out.println("main.Client connected.");

            // Input stream to read data sent by the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            int[] angles = new int[6];
            String receivedData;
            int count = 0;

            // Read angles sent by the client
            while ((receivedData = in.readLine()) != null) {
                try {
                    angles[count] = Integer.parseInt(receivedData);
                    count++;

                    // Once all 6 angles are received, store them on the blackboard
                    if (count == 6) {
                        blackboard.addAngles(angles);
                        System.out.println("Angles received and stored in blackboard.");
                        blackboard.displayAngles();  // Optional: Display stored angles
                        angles = new int[6];  // Reset for next batch of angles
                        count = 0;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid angle data received: " + receivedData);
                }
            }

            // Close the client socket when done
            clientSocket.close();
            System.out.println("Client disconnected.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

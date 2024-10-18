package v1;

import java.io.*;
import java.net.*;

public class CobotServer extends Thread {
    private final CobotModel model;

    public CobotServer(CobotModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started. Waiting for client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {
                int angle1 = Integer.parseInt(in.readLine());
                int angle2 = Integer.parseInt(in.readLine());
                int angle3 = Integer.parseInt(in.readLine());
                int angle4 = Integer.parseInt(in.readLine());
                int angle5 = Integer.parseInt(in.readLine());
                int angle6 = Integer.parseInt(in.readLine());

                System.out.println("Angles received: " + angle1 + ", " + angle2 + ", " + angle3 + ", " + angle4 + ", " + angle5 + ", " + angle6);

                // Store the angles in the singleton
                CobotAnglesSingleton.getInstance().setTargetAngles(angle1, angle2, angle3, angle4, angle5, angle6);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

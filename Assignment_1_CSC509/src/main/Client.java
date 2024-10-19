package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String input = "";
            while (true) {
                System.out.println("Enter 'exit' to stop or 'angles' to input angles: ");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                if (input.equalsIgnoreCase("angles")) {
                    System.out.println("Enter Angle 1: ");
                    int angle1 = scanner.nextInt();

                    System.out.println("Enter Angle 2: ");
                    int angle2 = scanner.nextInt();

                    System.out.println("Enter Angle 3: ");
                    int angle3 = scanner.nextInt();

                    System.out.println("Enter Angle 4: ");
                    int angle4 = scanner.nextInt();

                    System.out.println("Enter Angle 5: ");
                    int angle5 = scanner.nextInt();

                    System.out.println("Enter Angle 6: ");
                    int angle6 = scanner.nextInt();

                    // Send angles to the server
                    out.println(angle1);
                    out.println(angle2);
                    out.println(angle3);
                    out.println(angle4);
                    out.println(angle5);
                    out.println(angle6);

                    System.out.println("Angles sent: " + angle1 + ", " + angle2 + ", " + angle3 + ", " + angle4 + ", "
                            + angle5 + ", " + angle6);

                    // Consume newline left by nextInt
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid input, please type 'angles' to input angles or 'exit' to stop.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

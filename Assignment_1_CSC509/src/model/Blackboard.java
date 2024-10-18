package model;

import java.util.ArrayList;
import java.util.List;

public class Blackboard {
    private List<int[]> anglesList;

    public Blackboard() {
        anglesList = new ArrayList<>();
    }

    // Method to add angles to the blackboard
    public void addAngles(int[] angles) {
        anglesList.add(angles);
    }

    // Method to retrieve all stored angles
    public List<int[]> getAllAngles() {
        return anglesList;
    }

    // Display the angles stored in the blackboard
    public void displayAngles() {
        System.out.println("Stored Angles on the Blackboard:");
        for (int[] angles : anglesList) {
            System.out.print("[");
            for (int i = 0; i < angles.length; i++) {
                System.out.print(angles[i]);
                if (i < angles.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
}


package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blackboard {
    static private List<int[]> anglesList;
    static private Blackboard blackboard = null;

    private Blackboard() {
        anglesList = new ArrayList<>();
    }

    static public Blackboard Instance() {
        if (Objects.isNull(blackboard)) {
            blackboard = new Blackboard();
        }

        return blackboard;
    }

    // Method to add angles to the blackboard
    static public void addAngles(int[] angles) {
        anglesList.add(angles);
    }

    // Method to retrieve all stored angles
    static public List<int[]> getAllAngles() {
        return anglesList;
    }

    // Display the angles stored in the blackboard
    static public void displayAngles() {
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


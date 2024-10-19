package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class Blackboard {
    private int[] anglesArray;
    static private Blackboard blackboard = null;
    private PropertyChangeSupport propertyChangeSupport;
    private String event = "AnglesAdded";

    private Blackboard() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        anglesArray = new int[]{};
    }

    static public Blackboard Instance() {
        if (Objects.isNull(blackboard)) {
            blackboard = new Blackboard();
        }

        return blackboard;
    }

    public void AddListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    // Method to add angles to the blackboard
    public void SetAngles(int[] angles) {
        int[] oldArray = anglesArray;
        anglesArray = angles;
        //fire change
        propertyChangeSupport.firePropertyChange(event, oldArray, anglesArray);
    }

    // Method to retrieve all stored angles
    public int[] getAllAngles() {
        return anglesArray;
    }

    // Display the angles stored in the blackboard
    public void displayAngles() {
        System.out.println("Stored Angles on the Blackboard:");

        System.out.print("[");
        for (int i = 0; i < anglesArray.length; i++) {
            System.out.print(anglesArray[i]);
            if (i < anglesArray.length - 1) System.out.print(", ");
        }
        System.out.println("]");

    }
}


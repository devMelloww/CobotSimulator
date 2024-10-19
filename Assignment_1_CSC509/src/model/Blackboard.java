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
}


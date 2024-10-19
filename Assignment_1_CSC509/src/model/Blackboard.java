package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

/***
 * The Blackboard class is a singleton that serves as a central storage. It allows observers to listen for
 * changes to the angle data through property change events.
 */
public class Blackboard {
    private int[] anglesArray;
    static private Blackboard blackboard = null;
    private PropertyChangeSupport propertyChangeSupport;
    private String event = "AnglesAdded";

    /***
     * Private constructor to initialize the Blackboard instance. Initializes the anglesArray and sets up
     * property change support.
     */
    private Blackboard() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        anglesArray = new int[]{};
    }

    /***
     * Static method to get the singleton instance of Blackboard. If the instance does not exist, it creates one.
     * @return The singleton instance of Blackboard.
     */
    static public Blackboard Instance() {
        if (Objects.isNull(blackboard)) {
            blackboard = new Blackboard();
        }
        return blackboard;
    }

    /***
     * Adds a property change listener to the Blackboard.
     * @param listener Listener to be added.
     */
    public void AddListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /***
     * Sets the angles in the blackboard and notifies listeners of the change.
     * @param angles New array of angles to be stored.
     */
    public void SetAngles(int[] angles) {
        int[] oldArray = anglesArray;
        anglesArray = angles;
        //fire change
        propertyChangeSupport.firePropertyChange(event, oldArray, anglesArray);
    }

    /***
     * Retrieves all stored angles from the blackboard.
     * @return An array of all stored angles.
     */
    public int[] getAllAngles() {
        return anglesArray;
    }
}


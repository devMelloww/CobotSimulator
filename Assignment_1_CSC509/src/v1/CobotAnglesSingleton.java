package v1;

public class CobotAnglesSingleton {
    private static CobotAnglesSingleton instance;
    private int targetAngle1, targetAngle2, targetAngle3, targetAngle4, targetAngle5, targetAngle6;
    private boolean anglesReceived = false;

    // Private constructor to prevent instantiation
    private CobotAnglesSingleton() {}

    // Public method to get the singleton instance
    public static synchronized CobotAnglesSingleton getInstance() {
        if (instance == null) {
            instance = new CobotAnglesSingleton();
        }
        return instance;
    }

    // Set the target angles received from the client
    public synchronized void setTargetAngles(int angle1, int angle2, int angle3, int angle4, int angle5, int angle6) {
        this.targetAngle1 = angle1;
        this.targetAngle2 = angle2;
        this.targetAngle3 = angle3;
        this.targetAngle4 = angle4;
        this.targetAngle5 = angle5;
        this.targetAngle6 = angle6;
        anglesReceived = true; // Flag to indicate angles have been received
    }

    // Retrieve the target angles
    public synchronized int[] getTargetAngles() {
        return new int[]{targetAngle1, targetAngle2, targetAngle3, targetAngle4, targetAngle5, targetAngle6};
    }

    // Check if angles have been received
    public synchronized boolean areAnglesReceived() {
        return anglesReceived;
    }
}

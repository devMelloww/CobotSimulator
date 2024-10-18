package v1;

import java.util.ArrayList;
import java.util.List;

//property change listener

public class CobotModel {
    private int angle1, angle2, angle3, angle4, angle5, angle6;
    private int targetAngle1, targetAngle2, targetAngle3, targetAngle4, targetAngle5, targetAngle6;
    private final List<Observer> observers = new ArrayList<>();


    public void setAngles(int a1, int a2, int a3, int a4, int a5, int a6) {
        this.angle1 = a1;
        this.angle2 = a2;
        this.angle3 = a3;
        this.angle4 = a4;
        this.angle5 = a5;
        this.angle6 = a6;
        notifyObservers();
    }

    public int[] getAngles() {
        return new int[]{angle1, angle2, angle3, angle4, angle5, angle6};
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

interface Observer {
    void update();
}
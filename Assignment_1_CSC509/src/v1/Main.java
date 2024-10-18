package v1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        CobotModel model = new CobotModel();
        CobotView view = new CobotView(model);
        CobotController controller = new CobotController(model, view);

        // Start the server in a separate thread
        new CobotServer(model).start();
    }
}

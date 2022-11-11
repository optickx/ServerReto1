package app;

import logic.ControllerThread;

/**
 *
 * @author Roke
 */
public class App {

    public static void main(String[] args) throws Exception {
//Starts the controller of working threads
        ControllerThread controller = new ControllerThread();
        controller.run();

    }
}

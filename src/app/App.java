package app;

import logic.ControllerThread;

public class App {

    public static void main(String[] args) throws Exception {

        ControllerThread controller = new ControllerThread();
        controller.run();

    }
}

package app;

import logic.ControllerThread;

public class App {

    public static void main(String[] args) throws Exception {
        new ControllerThread().run();
    }
}

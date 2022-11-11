package app;

import logic.ControllerThread;

/**
 * @author dani
 */
public class App {

    /**
     * runs the threads.
     * @param args unused parameters
     * @throws Exception
     */
    
    public static void main(String[] args) throws Exception {
        new ControllerThread().run();

    }
}

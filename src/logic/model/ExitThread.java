/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.model;

import static java.lang.System.exit;
import java.util.Scanner;
import java.util.logging.Logger;
import logic.Pool;

/**
 *
 * @author 2dam
 */
public class ExitThread extends Thread {

    private boolean active = true;
    private static final Logger LOGGER = Logger.getLogger(ExitThread.class.getName());

    public void run() {
        while (active) {
            LOGGER.info("WRITE EXIT FOR CLOSING THE SERVER");
            if (new Scanner(System.in).next().trim().equalsIgnoreCase("EXIT")) {
                Pool.removeAll();
                active = false;
            }
        }
        LOGGER.info("SERVER CLOSED");
        exit(0);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package except;

/**
 *
 * @author 2dam
 */
public class ServerCapacityException extends Exception {

    /**
     * Creates a new instance of <code>ServerCapacityException</code> without
     * detail message.
     */
    public ServerCapacityException() {
        super("The server is full. Try again later.");
    }

    /**
     * Constructs an instance of <code>ServerCapacityException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ServerCapacityException(String msg) {
        super(msg);
    }
}

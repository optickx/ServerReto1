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
public class NotRegisteredException extends Exception {

    /**
     * Creates a new instance of <code>NotRegisteredException</code> without
     * detail message.
     */
    public NotRegisteredException() {
        super("User not registered o_O");
    }

    /**
     * Constructs an instance of <code>NotRegisteredException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotRegisteredException(String msg) {
        super(msg);
    }
}

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
public class LoginCredentialException extends Exception {

    /**
     * Creates a new instance of <code>LoginCredentialException</code> without
     * detail message.
     */
    public LoginCredentialException() {
        super("Error in the credentials");
    }

    /**
     * Constructs an instance of <code>LoginCredentialException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginCredentialException(String msg) {
        super(msg);
    }
}

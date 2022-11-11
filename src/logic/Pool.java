package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eneko
 */
public abstract class Pool {

    private static Stack stack = new Stack();
    private static Connection con;
    private static final ResourceBundle config =
        ResourceBundle.getBundle("resources.database_access");

    /**
     * @return the connection with the selected database
     */
    public static Connection openConnection() {
        con = null;
        try {
            con = DriverManager
                    .getConnection(config.getString("URL"), 
                    config.getString("USER"), 
                    config.getString("PASSWORD"));

            stack.add(con);
        } catch (SQLException ex) {
            Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    /**
     *
     * @return if the stack is empty it gives a new connection, else the stack
     * returns a connection
     */
    public static Connection getConnection() {
        if (!stack.empty()) {
            return (Connection) stack.pop();
        } else {
            return openConnection();

        }
    }

    public static void returnConnection(Connection con) {
        stack.push(con);
    }

    /**
     * When the server is closed, it gets all the connectios from the stack and
     * closes them
     */
    public static void removeAll() {
        while (!stack.isEmpty()) {
            con = (Connection) stack.pop();
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        stack.clear();
    }

}

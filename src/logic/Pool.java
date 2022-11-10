package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pool {
    
    private static Stack stack = new Stack();
    private static Connection con;
    private final ResourceBundle config
            = ResourceBundle.getBundle("resources.database_access");

    // store the credentials in local Strings.
    private final String url = config.getString("URL"),
            user = config.getString("USER"),
            pass = config.getString("PASS");
    
    public Connection openConnection() {
        con = null;
        try {
            con = DriverManager
                    .getConnection(url, user, pass);
            stack.add(con);
        } catch (SQLException ex) {
            Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public Connection getConnection() {
        if (!stack.empty()) {
            return (Connection) stack.pop();
        } else {
            return openConnection();
            
        }
    }
    
    public void returnConnection(Connection con) {
        stack.push(con);
    }
    
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

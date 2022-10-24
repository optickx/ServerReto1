/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto1server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 *
 * @author 2dam
 */
public class SQLAccess {
    public Connection con;
    // access to the database access credentials.
    private final ResourceBundle config = 
        ResourceBundle.getBundle("resources.database_access");

    // store the credentials in local Strings.
    private final String 
        url = config.getString("URL"),
        user = config.getString("USER"),
        pass = config.getString("PASSWORD");

    public Connection openConnection() {
        con = null;
        try {
            con = DriverManager
                .getConnection(url, user, pass);
            
        } catch (SQLException sqle) {
            // TODO: throw the exception.
        }
        return con;
    }

    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException sqle) {
            // TODO: handle exception.
        }
    }
}

package logic.model;

import except.EmailExistsException;
import except.LoginExistsException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.model.DBReaderImplementation;
import logic.objects.User;
import logic.objects.UserPrivilege;
import logic.objects.UserStatus;

/**
 * @author 2dam
 */
public class DBReaderImplementationTest {

    private static IDBReader idbr;

    private static User dani, nerea, roke, eneko;

    @Test
    public void testSignIn() {
        
    }

    @Test
    public void testSignUp() {
        try {
            assertNull(idbr.signUp(dani));
        } catch (LoginExistsException ex) {
            Logger.getLogger(DBReaderImplementationTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmailExistsException ex) {
            Logger.getLogger(DBReaderImplementationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void print(Object obj) {
        System.out.println(obj);
    }

    @BeforeClass
    public static void init() {
        dani = 
            new User(0, "opticks", "danielbarrios2002@gmail.com", 
            "Daniel Barrios Abad", "abcd*1234", null, UserStatus.ENABLED, 
            UserPrivilege.ADMIN, new ArrayList <Timestamp> ());

        initializeConnection();
    }

    private static void initializeConnection() {
        Connection pConnection = null;

        try {
            pConnection = DriverManager
            .getConnection(
            "jdbc:mysql://localhost:3306/signApp?serverTimezone=Europe/Madrid&useSSL=false",
            "root",
            "abcd*1234");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        idbr = new DBReaderImplementation(pConnection);
    }
}
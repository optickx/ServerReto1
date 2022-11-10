package logic.model;

import except.EmailExistsException;
import except.LoginCredentialException;
import except.LoginExistsException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

import logic.objects.User;

/**
 * @author dani
 */
@RunWith(OrderedRunner.class)
public class DBReaderImplementationTest {

    private static DBReaderImplementation idbr;

    private static final List<User> 
        randomLogged = 
            SampleUsers.randomUsers(20),
        randomNotLogged = 
            SampleUsers.randomUsers(15);

    @Test
    @Order(order = 0)
    public void testSignUp() {

        randomLogged.forEach(u -> {
            try {
                assertNotNull(idbr.signUp(u));
            } catch (LoginExistsException lee) {
                lee.printStackTrace(); // impossible case
            } catch (EmailExistsException eee) {
                eee.printStackTrace(); // impossible case
            }
        });

        randomLogged.forEach(u -> {
            try {
                assertNull(idbr.signUp(u));
            } catch (Exception e) {
                System.out.println("As expected.");
            }
        });
    }

    @Test
    @Order(order = 1)
    public void testSignIn() {
        randomLogged.forEach(u -> {
            try {
                assertNotNull(idbr.signIn(u));
                System.out.println(idbr.signIn(u).toString());
                System.out.println(idbr.signIn(u).toString());
            } catch (LoginCredentialException lce) {
                // TODO: handle exception
            }
        });

        randomNotLogged.forEach(u -> {
            try {
                assertNull(idbr.signIn(u));
            } catch (LoginCredentialException lce) {
                // TODO: handle exception
            }
        });
            
    }

    @Test
    @Order(order = 2)
    public void testGenerateID() {}

    /**
     * method that sets connection.
     */
    @BeforeClass
    public static void init() {
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
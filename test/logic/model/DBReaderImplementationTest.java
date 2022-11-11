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
import java.util.ResourceBundle;

import logic.objects.User;
import testManager.Order;
import testManager.OrderedRunner;

/**
 * @author dani
 */

@RunWith(OrderedRunner.class)
public class DBReaderImplementationTest {

    private static DBReaderImplementation idbr;

    // lists to store randomly generated users

    private static final List<User> 
        randomLogged = 
            UserGenerator.randomUsers(30),
        randomNotLogged = 
            UserGenerator.randomUsers(20);

    /**
     * registers all the user in randomLogged collection.
     * then checks that every User has been properly written.
     */

    @Test
    @Order(order = 0)
    public void testSignUp() {
        randomLogged.forEach(u -> {
            try {
                assertEquals(u, idbr.signUp(u).getUser());
            } catch (LoginExistsException lee) {
                lee.printStackTrace(); // impossible case
            } catch (EmailExistsException eee) {
                eee.printStackTrace(); // impossible case
            }
        });

        randomLogged.forEach(u -> {
            try {
                assertNull(idbr.signUp(u).getUser());
            } catch (Exception e) {
                // we should end in here.
                assertTrue(
                    e instanceof LoginExistsException ||
                    e instanceof EmailExistsException);
            }
        });
    }

    /**
     * checks, once again that every user has been checked,
     * and also that every 
     */

    @Test
    @Order(order = 1)
    public void testSignIn() {
        randomLogged.forEach(u -> {
            try {
                assertNotNull(idbr.signIn(u).getUser());
            } catch (LoginCredentialException lce) {
                //lce.printStackTrace(); // impossible
            }
        });

        randomNotLogged.forEach(u -> {
            try {
                assertNull(idbr.signIn(u).getUser());
            } catch (LoginCredentialException lce) {
                // 
            }
        });     
    }

    
    @Test
    @Order (order = 3)
    public void writeTeam() {
        List <User> l = UserGenerator.teamUsers();

        System.out.println(l.size());

        for (User user : l) 
            System.out.println(user);
        
        l.forEach(u -> {
            try {
                idbr.signUp(u).getUser();
            } catch (Exception e) {
                System.out.println("somehow failed");
                e.printStackTrace();
            }
        });
    }

    /**
     * executed before any test, loads the connection
     * so that we can access the database.
     */
    @BeforeClass
    public static void init() {

        ResourceBundle rb = 
            ResourceBundle
                .getBundle("resources.database_access");
        try {
            idbr = 
                new DBReaderImplementation(
                    DriverManager
                        .getConnection(
                            rb.getString("URL"),
                            rb.getString("USER"),
                            rb.getString("PASSWORD")));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }   
    }
}

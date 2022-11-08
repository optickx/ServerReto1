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

/**
 * @author dani
 */

@RunWith(OrderedRunner.class)
public class DBReaderImplementationTest {

    private static DBReaderImplementation idbr;

    // lists to store randomly generated users

    private static final List<User> 
        randomLogged = 
            SampleUsers.randomUsers(2),
        randomNotLogged = 
            SampleUsers.randomUsers(1);

    /**
     * registers all the user in randomLogged collection.
     * then checks that every User has been properly written.
     */

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

    /**
     * checks, once again that every user has been checked,
     * and also that every 
     */

    @Test
    @Order(order = 1)
    public void testSignIn() {
        randomLogged.forEach(u -> {
            try {
                assertNotNull(idbr.signIn(u));
            } catch (LoginCredentialException lce) {
                lce.printStackTrace(); // impossible
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
    public void testGenerateID() {
        int assID = -1;
        if (!randomLogged.isEmpty())
            assID = randomLogged.stream()
                .max((u1, u2) -> 
                    u1.getID() - u2.getID())
                        .get().getID() + 1;

        if (assID != -1)
            assertEquals(assID, idbr.generateID());

        assertNotEquals(assID, -1);
        
    }

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
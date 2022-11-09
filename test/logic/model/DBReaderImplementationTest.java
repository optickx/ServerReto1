package logic.model;

import except.EmailExistsException;
import except.LoginCredentialException;
import except.LoginExistsException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
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
            SampleUsers.randomUsers(20),
        randomNotLogged = 
            SampleUsers.randomUsers(20);

    /**
     * registers all the user in randomLogged collection.
     * then checks that every User has been properly written.
     */

    @Test
    @Order(order = 0)
    public void testSignUp() {

        randomLogged.forEach(u -> {
            try {
                assertEquals(u, idbr.signUp(u));
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
                assertNotNull(idbr.signIn(u));
            } catch (LoginCredentialException lce) {
                //lce.printStackTrace(); // impossible
            }
        });

        randomNotLogged.forEach(u -> {
            try {
                assertNull(idbr.signIn(u));
            } catch (LoginCredentialException lce) {
                // 
            }
        });     
    }

    /**
     * checks that the IDs are generated correctly.
     */

    @Test
    @Order(order = 2)
    public void testGenerateID() {
        // maximum possible ID.
        int total = 0;
        try {
            ResultSet rs =  idbr
                .getConnection()
                    .prepareStatement("SELECT COUNT(*) FROM" + idbr.tableName)
                        .executeQuery();
            total = rs.getInt(1);
        } catch (Exception e) {
            // TODO: handle exception
        }
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
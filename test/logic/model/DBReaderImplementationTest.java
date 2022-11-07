package logic.model;

import except.EmailExistsException;
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
            SampleUsers.randomUsers(),
        randomNotLogged = 
            SampleUsers.randomUsers();

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
            assertNotNull(idbr.signIn(u));
            System.out.println(idbr.signIn(u).toString());
        });

        randomNotLogged.forEach(u -> 
            assertNull(idbr.signIn(u)));
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

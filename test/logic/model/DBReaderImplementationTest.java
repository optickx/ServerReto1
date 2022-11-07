package logic.model;

import except.EmailExistsException;
import except.LoginExistsException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;

import logic.objects.User;

/**
 * @author dani
 */
public class DBReaderImplementationTest {

    private static DBReaderImplementation idbr;

    private static List <User> team = 
        SampleUsers.teamUsers();

    private static List <User> random =
        SampleUsers.randomUsers();

    @Test
    public void testSignIn() {
        


    }

    @Test
    public void testSignUp() {
        /*
        users from the team that
        are already at the database.
        */ 

        List <User> already =
            new ArrayList <User> ();

        for (int i = 0; i < team.size()/2; i++)
            try {
                assertNotNull(idbr.signUp(team.get(i)));   
            } catch (EmailExistsException eee) {
                System.out.println("Impossible case.");
            } catch (LoginExistsException lee) {
                System.out.println("Impossible case.");
            }
        
        // now we save half of the values that are not in the table.

        team.forEach(u -> {
            if (idbr.signIn(u) != null)
                already.add(u);
            });

        already.forEach(u -> {
            try {
                idbr.signUp(u);   
            } catch (EmailExistsException eee) {
                assertEquals(u.getEmail(), idbr.signIn(u).getEmail());
                assertTrue(already.contains(u));
            } catch (LoginExistsException lee) {
                assertEquals(u.getLogin(), idbr.signIn(u).getLogin());
                assertTrue(already.contains(u));
            }
        }); 

        random.forEach(u -> {
            try {
                idbr.signUp(u);
            } catch (EmailExistsException eee) {
                System.out.println("Impossible case.");
            } catch (LoginExistsException lee) {
                System.out.println("Impossible case");
            }
        });
    }

    @Test
    public void testGenerateID() {
        System.out.println(idbr.generateID());
    }

    @BeforeClass
    public static void init() {
        

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

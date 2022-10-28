/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package logic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import logic.DBAFactory;
import logic.objects.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author dani
 */
public class DBReaderImplementationTest {
    
    @BeforeAll
    public static void setUpClass() {
        Connection con = null;
        ResourceBundle rb = 
            ResourceBundle
                .getBundle("resources.database_access");

        try {
            con = DriverManager
                .getConnection(
                    rb.getString("URL"),
                    rb.getString("USER"),
                    rb.getString("PASSWORD"));
        } catch (SQLException sqle) {
            // TODO: handle exception
        }

        DBAFactory.provisionalSystemStartConnection(con);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of signIn method, of class DBReaderImplementation.
     */
    @Test
    public void testSignIn() {
        System.out.println("signIn");
        User pUser = null;
        DBReaderImplementation instance = null;
        User expResult = null;
        User result = instance.signIn(pUser);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of signUp method, of class DBReaderImplementation.
     */
    @Test
    public void testSignUp() {
        User u2, u1 =
            new User(
            0, "opticks", "danielbarrios2002@gmail.com", 
            "Daniel Barrios", "abcd*1234",
            Timestamp.valueOf(LocalDateTime.now()), 1, 1, null);

        System.out.println(u1.toString());

        u2 = DBAFactory.getAccess().signUp(null);

        assertEquals(u1, u2);
        System.out.println(u2);
    }

    /**
     * Test of generateID method, of class DBReaderImplementation.
     */
    @Test
    public void testGenerateID() {
        System.out.println("generateID");
        DBReaderImplementation instance = null;
        int expResult = 0;
        int result = instance.generateID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnection method, of class DBReaderImplementation.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        DBReaderImplementation instance = null;
        Connection expResult = null;
        Connection result = instance.getConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

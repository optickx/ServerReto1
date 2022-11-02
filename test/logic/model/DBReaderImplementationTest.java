/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package logic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    
    public static DBReaderImplementation dbi;
    
    @BeforeEach
    public static void setUpClass() {
        Connection c = null;
        try {
            c = DriverManager
                .getConnection(
                "url",
                "root",
                "abcd*1234");
        } catch (SQLException sqle) {

        }
        
        dbi =
            new DBReaderImplementation(c);
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
        
        
    }

    /**
     * Test of signUp method, of class DBReaderImplementation.
     */
    @Test
    public void testSignUp() {
        
        

    }

    /**
     * Test of generateID method, of class DBReaderImplementation.
     */
    @Test
    public void testGenerateID() {
        
    }

    /**
     * Test of getConnection method, of class DBReaderImplementation.
     */
    @Test
    public void testGetConnection() {
        
        
    }
    
}

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


public class UserTest {

    @Test
    public void equalsTest() {
        
    }
}

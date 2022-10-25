package logic.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logic.objects.User;
import logic.objects.UserPrivilege;
import logic.objects.UserStatus;

public class DBReaderImplementation implements IDBReader {
    /**the value of the connection is given
     * at the constructor. should be used
     */
    private final Connection con;
    private ResultSet rs;

    public DBReaderImplementation(Connection pConnection) {
        con = pConnection;
    }
    
    @Override
    public User signIn(User pUser) {
        try {
            stmt = con.prepareStatement(signIn);
                stmt.setString(1, pUser.getLogin());
                stmt.setString(2, pUser.getPassword());
            rs = stmt.executeQuery();

            if (rs.next())
                
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public User signUp(User pUser) {
        try {
            stmt = con.prepareStatement(signUp);
                stmt.setInt(0, count() + 1);
                stmt.setString(1, pUser.getLogin());
                stmt.setString(2, pUser.getEmail());
                stmt.setString(3, pUser.getFullName());
                stmt.setString(4, pUser.getPassword());
                // TODO: LAST PASSWORD CHANGE.

                stmt.setInt(0, 
                    (pUser.getPrivilege() == UserPrivilege.ADMIN) ? 1 : 0);
                
                stmt.setInt(0,
                    (pUser.getStatus() == UserStatus.ENABLED) ? 1 : 0);

                // TODO: last login.
                

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    private int count() {
        int c = 0;
        try {
            rs = con.prepareStatement(count).executeQuery();
            if (rs.next())
                c = rs.getInt(1);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return c;
    }

    private PreparedStatement stmt;
    
    private final String count =
        "SELECT COUNT(*) FROM user";

    private final String signUp =
        "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?, ?), COUNT(*)";

    private final String signIn =
        "SELECT LOGIN, PASSWORD FROM USER WHERE LOGIN = ? AND PASSWORD = ?";
}
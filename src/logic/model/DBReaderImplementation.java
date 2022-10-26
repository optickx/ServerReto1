package logic.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
                return 
                    new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getTimestamp(6),
                    rs.getInt(7),
                    rs.getInt(8),
                    getLastSignIns(pUser.getLogin()));

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        return null;
    }
    /**@param pLogin is the login of the user whose last
     * logins we want to know.
     * @return a List of Timestamps, chronologically sorted.
     */
    private List <Timestamp> getLastSignIns(String pLogin) {
        List <Timestamp> l =
            new ArrayList <Timestamp> ();
        
        try {
            stmt = con.prepareStatement(lastSignIns);
                stmt.setString(0, pLogin);
            rs = stmt.executeQuery();
            
            while (rs.next())
                l.add(rs.getTimestamp(1));

        } catch (SQLException sqle) {
           sqle.printStackTrace();
        }

        l.stream()
            .sorted((t1, t2) -> t1.compareTo(t2));
        return l;
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
                stmt.setTimestamp(5, pUser.getLastPasswordChange());
                stmt.setInt(6, 
                    (pUser.getPrivilege() == UserPrivilege.ADMIN) ? 1 : 0);
                
                stmt.setInt(7,
                    (pUser.getStatus() == UserStatus.ENABLED) ? 1 : 0);

                stmt.executeUpdate();

                return pUser;
                

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }
    /**@return the exact number of users in the table.
     * it can be used to generate the user ID.
    */
    private int count() {
        int c = 0;
        /**note that the PreparedStatement is not being used.
         * if we did, we would modify the prepared statement 
         * from the function calling this code and this would
         * give errors.
         */
        try {
            rs = con.prepareStatement(count).executeQuery();
            if (rs.next())
                c = rs.getInt(1);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return c;
    }

    // we do not have to create in every module.
    private PreparedStatement stmt;
    
    private final String count =
        "SELECT COUNT(*) FROM user";

    private final String lastSignIns =
        "SELECT lastSignIn from signIn WHERE id = ?";

    private final String signUp =
        "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?, ?), COUNT(*)";

    private final String signIn =
        "SELECT login, password FROM user WHERE login = ? AND password = ?";
}
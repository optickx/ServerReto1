package logic.model;

import except.EmailExistsException;
import except.LoginExistsException;
import except.UserExistsException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.security.auth.login.LoginException;

import logic.objects.*;
import pool.CPool;

public class DBReaderImplementation implements IDBReader {

    /**
     * the value of the connection is given at the constructor. should be used
     * with every database usage in this class.
     */
    private Connection con;

    /**
     * to the database is necessary to give a connection to this database.
     *
     * @param pConnection will be used by every module.
     */
    public DBReaderImplementation() {
        CPool pool = new CPool();
        this.con = pool.getConnection();
    }

    /**
     * selects a user from the table using the values of the login and the
     * password. theoretically, the user should have only, and this means ONLY,
     * the login and the password.
     *
     * @param user object that contains the credentials.
     * @return a new User with all it's data. if there's nothing found, an empty
     * (null) value.
     */
    @Override
    public User signIn(User user) {
        try {
            stmt = con.prepareStatement(signIn);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();

            /**
             * the values of the last SignIns are obtained by using the ID to
             * seach it.
             */
            if (rs.next()) {
                return new User();
            }
            /*        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        selectLastSignIns(rs.getInt(0)));*/

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (NullPointerException npe) {
            // this should never happen.
            npe.printStackTrace();
        }

        return null;
    }

    /**
     * @param id is the ID of the user whose logins will be searched.
     * @return a List of Timestamps, chronologically sorted.
     */
    private List<Timestamp> selectLastSignIns(int id) {
        List<Timestamp> l
                = new ArrayList<Timestamp>();

        try {
            stmt = con.prepareStatement(lastSignIns);
            stmt.setInt(0, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                l.add(rs.getTimestamp(1));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        l.stream()
                .sorted((t1, t2) -> t1.compareTo(t2));

        return l;
    }

    /**
     * method that writes a new User to the database.
     *
     * @param user is the user to be written. the attributes CANNOT BE NULL.
     */
    @Override
    public User signUp(User user) {
        try {
            stmt = con.prepareStatement(loginExistsCheck);
            stmt.setString(0, user.getLogin());
            ResultSet rs = stmt.executeQuery();
            if (rs.next() == false) {
                throw new LoginExistsException();
            }
            
            stmt = con.prepareStatement(signUp);
            stmt.setInt(0, generateID());
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getPassword());
            stmt.setTimestamp(5, user.getLastPasswordChange());

            stmt.setInt(6,
                    (user.getPrivilege() == UserPrivilege.ADMIN) ? 1 : 0);

            stmt.setInt(7,
                    (user.getStatus() == UserStatus.ENABLED) ? 1 : 0);

            int exceptionCheck = stmt.executeUpdate();
            if (exceptionCheck == 2) {
                throw new EmailExistsException();
            }

        } catch (LoginExistsException e) {

        } catch (EmailExistsException e) {
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return user;
    }

    /**
     * this method generates the ID correspondant to the new user to be written
     * to this database. in any case, it's an ID that IT'S NOT BEING USED YET.
     *
     * @return a new ID.
     */
    public int generateID() {
        List<Integer> l
                = new ArrayList<Integer>();

        try {

            /**
             * note that the PreparedStatement is not being used. if we did, we
             * would modify the prepared statement from the function calling
             * this code and this could give errors.
             */
            ResultSet rs = con.prepareStatement(everyID)
                    .executeQuery();

            while (rs.next()) {
                l.add(rs.getInt(0));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        if (l.isEmpty()) {
            return 1;
        } else if (l.contains(l.size())) {
            return l.size() + 1;
        }

        /**
         * in case of not having a "perfect" case of IDs, we give the new one.
         */
        return l.stream()
                .max((i1, i2) -> i1 - i2).get() + 1;

    }

    /**
     * just a simple getter.
     *
     * @return the connection being used in this class.
     */
    public Connection getConnection() {
        return con;
    }

    // we do not have to create in every module.
    private PreparedStatement stmt;

    private final String count
            = "SELECT COUNT(*) FROM user";

    private final String everyID
            = "SELECT ID from user";

    private final String lastSignIns
            = "SELECT lastSignIn from signIn WHERE id = ?";

    private final String signUp
            = "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?, ?), COUNT(*)";

    private final String signIn
            = "SELECT login, password FROM user WHERE login = ? AND password = ?";

    private final String loginExistsCheck
            = "SELECT login FROM user WHERE login = ?";
}

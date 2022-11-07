package logic.model;

import except.EmailExistsException;
import except.LoginCredentialException;
import except.LoginExistsException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import logic.objects.*;

public class DBReaderImplementation implements IDBReader {

    /**
     * the value of the connection is given at the constructor. should be used
     * with every database usage in this class.
     */
    private final Connection con;

    /**
     * to the database is necessary to give a connection to this database.
     *
     * @param pConnection will be used by every module.
     */
    public DBReaderImplementation(Connection pConnection) {
        con = pConnection;
    }

    /**
     * selects a user from the table using the values of the login and the
     * password. theoretically, the user should have only, and this means ONLY,
     * the login and the password.
     *
     * @param user
     * @param pUser object that contains the credentials.
     * @return a new User with all it's data. if there's nothing found, an empty
     * (null) value.
     * @throws except.LoginCredentialException
     */
    @Override
    public User signIn(User user) throws LoginCredentialException {
        try {
            stmt = con.prepareStatement(signIn);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            /**
             * the values of the last SignIns are obtained by using the ID to
             * seach it.
             */
            int ID
                    = rs.getInt("id");

            insertSignIn(ID, rightNow());

            user = new User(
                    ID,
                    rs.getString("email"),
                    rs.getString("fullName"),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getTimestamp(6),
                    rs.getInt(7),
                    rs.getInt(8),
                    selectLastLogins(ID));

        } catch (SQLException sqle) {
            sqle.printStackTrace();// throw new LoginCredentialException();
        }
        return user;
    }

    /**
     * writes a new User to the database.
     *
     * @param pUser is the user to be written. the attributes CANNOT BE NULL.
     * @return the same user if everything worked, and null if they where any
     * errors, plus exception.
     * @throws except.LoginExistsException
     * @throws except.EmailExistsException
     */
    @Override
    public User signUp(User pUser) throws LoginExistsException, EmailExistsException {
        try {
            if (loginExists(pUser.getLogin())) {
                throw new LoginExistsException();
            }

            if (emailExists(pUser.getEmail())) {
                throw new EmailExistsException();
            }

            int ID = generateID();

            stmt = con.prepareStatement(signUp);
            stmt.setInt(1, ID);
            stmt.setString(2, pUser.getLogin());
            stmt.setString(3, pUser.getEmail());
            stmt.setString(4, pUser.getFullName());
            stmt.setString(5, pUser.getPassword());
            stmt.setTimestamp(6, rightNow());

            stmt.setInt(7,
                    (pUser.getPrivilege() == UserPrivilege.ADMIN) ? 1 : 0);

            stmt.setInt(8,
                    (pUser.getStatus() == UserStatus.ENABLED) ? 1 : 0);

            stmt.executeUpdate();

            pUser.setID(ID);

            if (!pUser.getLastLogins().isEmpty()) {
                pUser.getLastLogins().forEach(l
                        -> insertSignIn(ID, l));
            }

            insertSignIn(ID, rightNow());

            return pUser;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        return null;
    }

    protected boolean emailExists(String pEmail) {
        try {
            stmt = con.prepareStatement(checkEmail);
            stmt.setString(1, pEmail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException sqle) {
            // TODO: handle exception
        }
        return false;
    }

    /**
     * tells if exists or not obsivus
     */
    protected boolean loginExists(String pLogin) {
        try {
            stmt = con.prepareStatement(checkLogin);
            stmt.setString(1, pLogin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException sqle) {
            // TODO: handle exception
        }
        return false;
    }

    /**
     * @param pID is the ID of the user whose logins will be searched.
     * @return a List of Timestamps, chronologically sorted.
     */
    private List<Timestamp> selectLastLogins(int pID) {
        List<Timestamp> l
                = new ArrayList<Timestamp>();

        try {
            stmt = con.prepareStatement(lastSignIns);

            stmt.setInt(1, pID);
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

    private boolean insertSignIn(int pID, Timestamp pSignIn) {
        try {
            stmt = con.prepareStatement(insertSignIn);
            stmt.setTimestamp(1, pSignIn);
            stmt.setInt(2, pID);
            stmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * this method generates the ID correspondant to the new user to be written
     * to this database. in any case, it's an ID that IT'S NOT BEING USED YET.
     *
     * @return a new ID.
     */
    protected int generateID() {
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
                l.add(rs.getInt(1));
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
     * @return this instant timestamp
     */
    private Timestamp rightNow() {
        return Timestamp.valueOf(LocalDateTime.now());
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

    private final String checkLogin
            = "SELECT login FROM user WHERE login = ?";

    private final String checkEmail
            = "SELECT email FROM user WHERE email = ?";

    //private final String count = 
    //    "SELECT COUNT(*) FROM user";
    private final String everyID
            = "SELECT id FROM user";

    private final String lastSignIns
            = "SELECT lastSignIn FROM signin WHERE id = ?";

    private final String signUp
            = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final String signIn
            = "SELECT * FROM user WHERE login = ? AND password = ?";

    private final String insertSignIn
            = "INSERT INTO signIn VALUES (?, ?)";

}

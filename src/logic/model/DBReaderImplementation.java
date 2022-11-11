package logic.model;

import except.EmailExistsException;
import except.LoginCredentialException;
import except.LoginExistsException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import logic.objects.*;
import logic.objects.message.Response;

/**
 *
 * @author Eneko,Roke
 */
public class DBReaderImplementation implements IClientServer {

    /**
     * the value of the connection is given at the constructor. should be used
     * with every database usage in this class.
     */
    private final Connection con;

    /**
     * to the database is necessary to give a connection to this database.
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
     * @param user object that contains the credentials.
     * @return a new User with all it's data. if there's nothing found, an empty
     * (null) value.
     * @throws except.LoginCredentialException
     */

    @Override
    public Response signIn(User user) throws LoginCredentialException {
        Response response = null;
        try {
            stmt = con.prepareStatement(signIn);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();

            /**
             * the values of the last SignIns are obtained by using the ID to
             * search it.
             */
            if (rs.next()) {
                int ID = rs.getInt("id");
                insertSignIn(ID, rightNow());
                user = new User(
                        ID,
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        selectLastLogins(ID));
                response = new Response();
                response.setUser(user);
            } else {

                int ID = 0;

                if (rs.next()) {
                    ID
                            = rs.getInt("id");
                } else {
                    throw new LoginCredentialException();
                }
            }
        } catch (SQLException sqle) {
            throw new LoginCredentialException();
        }
        return response;
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
    public Response signUp(User pUser) throws LoginExistsException, EmailExistsException {
        Response response = null;
        try {
            if (loginExists(pUser.getLogin())) {
                throw new LoginExistsException();
            } else if (emailExists(pUser.getEmail())) {
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

            List<Timestamp> l
                    = pUser.getLastLogins();
            if (l != null) {
                if (l.size() >= 10) {
                    for (int i = 0; 9 < l.size(); i++) {
                        l.remove(i);
                    }
                }
            } else {
                l = new ArrayList<Timestamp>();
            }

            l.add(rightNow());

            l.forEach(t -> insertSignIn(ID, t));

            pUser.setLastLogins(l);
            response = new Response();
            response.setUser(pUser);

        } catch (SQLException sqle) {
            // sqle.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        return response;
    }

    /**
     * @param pEmail the email to be checked in the table user.
     * @return true if the email exists, false if not
     */

    protected boolean emailExists(String pEmail) {
        try {
            stmt = con.prepareStatement(checkEmail);
            stmt.setString(1, pEmail);

            if (stmt.executeQuery().next()) {
                return true;
            }

        } catch (SQLException sqle) {
            // TODO: what the fuck, seriously, what the fuck
        }
        return false;
    }

    /**
     * @param pLogin the login to be checked in the table user.
     * @return true if the email exists, false if not.
     */

    protected boolean loginExists(String pLogin) {
        try {
            stmt = con.prepareStatement(checkLogin);
            stmt.setString(1, pLogin);

            if (stmt.executeQuery().next()) {
                return true;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
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
            //sqle.printStackTrace();
        }

        l.stream()
                .sorted((t1, t2) -> t1.compareTo(t2));

        return l;
    }

    /**
     * @param pID id of the user
     * @param pSignIn date to be written
     * @return true if not errors, false if something went wrong.
     */

    private boolean insertSignIn(int pID, Timestamp pSignIn) {
        try {
            PreparedStatement pstmt
                    = con.prepareStatement(insertSignIn);
            pstmt.setTimestamp(1, pSignIn);
            pstmt.setInt(2, pID);
            pstmt.executeUpdate();

        } catch (SQLException sqle) {
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
            //sqle.printStackTrace();
        }

        int i = 1;

        if (l.isEmpty()) {
            return i;
        } else if (l.contains(l.size())) {
            while (l.contains(i)) {
                i++;
            }

            return i;
        }

        //in case of not having a "perfect" case of IDs, we give the new one.
        return l.stream()
                .max((i1, i2) -> i1 - i2).get() + 1;
    }

    protected int count() { //unused
        try {
            ResultSet rs
                    = con.prepareStatement(count).executeQuery();
            if (rs.next()) // obviusly if nothing went wrong, there's a value
            {
                return rs.getInt(1);
            }

        } catch (SQLException sqle) {
            // TODO: handle exception
            sqle.printStackTrace();
        }

        return -1;
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
            = "SELECT * FROM user WHERE login = ?";

    private final String checkEmail
            = "SELECT * FROM user WHERE email = ?";

    private final String count
            = "SELECT COUNT(*) FROM user";

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

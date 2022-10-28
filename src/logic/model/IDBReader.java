package logic.model;

import except.LoginCredentialException;
import except.UserExistsException;
import java.sql.SQLException;
import logic.objects.User;

public interface IDBReader {

    /**
     * @return
     *
     */
    public User signUp(User pUser) throws UserExistsException, LoginCredentialException, SQLException;

    /**
     * @return
     *
     */
    public User signIn(User pUser);

}

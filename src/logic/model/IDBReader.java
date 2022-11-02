package logic.model;

import except.EmailExistsException;
import except.LoginCredentialException;
import except.LoginExistsException;
import except.UserExistsException;
import java.sql.SQLException;
import logic.objects.User;

public interface IDBReader {

    /**
     * @return
     *
     */
    public User signUp(User pUser) throws EmailExistsException, LoginExistsException, SQLException, Exception;

    /**
     * @return
     *
     */
    public User signIn(User pUser);

}

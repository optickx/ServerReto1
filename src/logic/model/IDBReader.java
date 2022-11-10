package logic.model;

import except.EmailExistsException;
import except.LoginCredentialException;
import except.LoginExistsException;
import except.NotRegisteredException;
import logic.objects.User;

public interface IDBReader {

    /**
     * @return
     *
     */
    public User signUp(User pUser) throws LoginExistsException, EmailExistsException, NotRegisteredException;

    /**
     * @return
     *
     */
    public User signIn(User pUser) throws LoginCredentialException;

}

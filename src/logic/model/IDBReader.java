package logic.model;

import except.EmailExistsException;
import except.LoginCredentialException;
import except.LoginExistsException;
import except.NotRegisteredException;
import logic.objects.User;

public interface IDBReader {

    /**
     * @return the same user if everything worked, 
     * null and exception if any error. 
     * @throws LoginExistsException
     * @throws EmailExistsException
     * @throws NotRegisteredException
     *
     */
    public User signUp(User pUser) throws LoginExistsException, EmailExistsException, NotRegisteredException;

    /**
     * @return the same user if eveything worked,
     * null and exception if any error.
     * @throws LoginCredentialException
     *
     */
    public User signIn(User pUser) throws LoginCredentialException;

}

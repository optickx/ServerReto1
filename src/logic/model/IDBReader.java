package logic.model;

import except.EmailExistsException;
import except.LoginExistsException;
import logic.objects.User;

public interface IDBReader {
    /**
     * @return
     * 
     */
    public User signUp(User pUser) throws LoginExistsException, EmailExistsException;

    /**
     * @return
     * 
     */
    public User signIn(User pUser);

}
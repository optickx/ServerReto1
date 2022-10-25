package logic.model;

import logic.objects.User;

public interface IDBReader {
    /**
     * @return
     * 
     */
    public User signUp(User pUser);

    /**
     * @return
     * 
     */
    public User signIn(User pUser);

}
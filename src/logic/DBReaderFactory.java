package logic;

import java.sql.Connection;

import logic.model.DBReaderImplementation;
import logic.model.IClientServer;

/**
 * @author dani
 */
public abstract class DBReaderFactory {

    private static IClientServer idbr = null;

    /**
     * @return an implementation of the interface
     */
    public static IClientServer getAccess() {
        if (idbr == null) 
            idbr = new DBReaderImplementation(Pool.getConnection());

        return idbr;
    }
}

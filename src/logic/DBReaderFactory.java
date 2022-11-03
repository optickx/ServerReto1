package logic;

import java.sql.Connection;

import logic.model.DBReaderImplementation;
import logic.model.IDBReader;

public abstract class DBReaderFactory {
    private static IDBReader idbr = null;

    public static IDBReader getAccess() {
        if (idbr == null)
            idbr = 
                new DBReaderImplementation(
                    Pool.getConnection());
        
        return idbr;
    }

    /** delete after testing.
     */
    public static void provisionalSystemStartConnection(Connection pConnection) {
        idbr = new DBReaderImplementation(pConnection);
    }
}
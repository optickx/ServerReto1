package logic;

import java.sql.Connection;

import logic.model.DBReaderImplementation;
import logic.model.IDBReader;

public abstract class DBReaderFactory {

    private static IDBReader idbr = null;

    public static IDBReader getAccess() {
        if (idbr == null) {
            Pool pool = new Pool();
            idbr= new DBReaderImplementation(pool.getConnection());
        }

        return idbr;
    }
}

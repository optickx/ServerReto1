package logic;

import java.sql.Connection;

import logic.model.DBReaderImplementation;
import logic.model.IClientServer;

public abstract class DBReaderFactory {

    private static IClientServer idbr = null;

    public static IClientServer getAccess() {
        if (idbr == null) {
            Pool pool = new Pool();
            idbr = new DBReaderImplementation(pool.getConnection());
        }

        return idbr;
    }
}

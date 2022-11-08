package logic;

import logic.model.DBReaderImplementation;
import logic.model.IDBReader;

public abstract class DBReaderFactory {

    private static IDBReader idbr = null;

    public static IDBReader getAccess() {
        if (idbr == null) 
            idbr = 
                new DBReaderImplementation(
                    new Pool()
                        .getConnection());

        return idbr;
    }
}

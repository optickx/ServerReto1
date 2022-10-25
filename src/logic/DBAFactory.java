package logic;

import logic.model.DBReaderImplementation;
import logic.model.IDBReader;

public abstract class DBAFactory {
    private static IDBReader idbr = null;

    public IDBReader get() {
        if (idbr == null)
            idbr = new DBReaderImplementation(null);
        
            return idbr;
    }
}
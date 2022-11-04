package logic;

import java.sql.Connection;
import java.util.Stack;

public class Pool {

    private SQLAccess sql = new SQLAccess();
    private static Stack stack = new Stack();
    private Connection con;

    public Connection getConnection() {
        if (!stack.empty()) {
            return (Connection) stack.pop();
        } else {
            return sql.openConnection();
        }
    }

    public void returnConnection(Connection con) {
        stack.push(con);
    }

    public void removeAll() {
        stack.clear();
    }

}

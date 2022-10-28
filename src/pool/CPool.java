package pool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.util.Stack;
import logic.model.connections.SQLAccess;

/**
 *
 * @author 2dam
 */
public class CPool {
    private SQLAccess sql = new SQLAccess();
    private static Stack stack = new Stack();
    private Connection con;
    
    public Connection getConnection(){
        if(!stack.empty()){
            return (Connection) stack.pop();
        }
        else{
            return sql.openConnection();
        }
    }
    
    public void returnConnection(Connection con){
        stack.push(con);
    }
    
    public void removeAll(){
        stack.clear();
    }
}
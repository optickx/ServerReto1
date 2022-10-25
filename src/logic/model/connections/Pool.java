/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.model.connections;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author 2dam
 */
public class Pool {
   private SQLAccess sql = new SQLAccess();
   private static ArrayList<SConnection> connections = new ArrayList<SConnection>();
   private SConnection con = null;
   
   public Connection getConnection(){
       for(int i = 0; i < connections.size(); i++){
           if(!connections.get(i).isActive()){
               connections.get(i).setActive(true);
               return connections.get(i).getConnection();
           }
       }
       con.setConnection(sql.openConnection());
       con.setActive(true);
       connections.add(con);
       return con.getConnection();
   }
   
   public void freeConnection(Connection con){
       for(int i = 0; i<connections.size(); i++){
           if(connections.get(i).equals(con)){
               connections.get(i).setActive(false);
               i = connections.size();
           }
       }
   }
   
   public void removeConnection(Connection con){
       for(int i = 0; i<connections.size(); i++){
           if(connections.get(i).equals(con)){
               sql.closeConnection(con);
               connections.remove(i);
               i = connections.size();
           }
       }
   }
   
}

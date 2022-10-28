/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.model;

/**
 *
 * @author 2dam
 */
public class DBReaderFactory {
    public static IDBReader getAccess(){
        return new DBReaderImplementation();
    }
}

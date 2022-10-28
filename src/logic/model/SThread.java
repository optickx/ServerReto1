/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.model;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import logic.objects.message.Request;

/**
 *
 * @author 2dam
 */
public class SThread extends Thread{
    private Socket socket;
    private Request request;
    
    public SThread(Socket socket){
        this.socket = socket;
    }
    
    public void run(){
        ObjectOutputStream write = null;
        ObjectInputStream read = null;
        try{
            read = new ObjectInputStream(socket.getInputStream());
            request = (Request) read.readObject();
            IDBReader dbReader = DBReaderFactory.getAccess();
            if(request.getRequestType().equals("SIGNIN")){
                System.out.println("logic.model.SThread.run()");
            }
            if(request.getRequestType().equals("SIGNUP")){
                System.out.println("signup");
            }
        } catch(Exception e){
            
        }
        
    }
}

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
import logic.objects.message.types.RequestType;

/**
 *
 * @author 2dam
 */
public class SThread extends Thread {

    private Request request;

    public SThread(Request request) {
        this.request = request;
    }

    public void run() {
        try {
            IDBReader dbReader = DBReaderFactory.getAccess();
            
            if (request.getRequestType().equals(RequestType.SIGNIN)) {
                dbReader.signIn(request.getUser());
            }
            if (request.getRequestType().equals(RequestType.SIGNUP)) {
                dbReader.signUp(request.getUser());
            }
            

        } catch (Exception e) {

        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.model;

import except.EmailExistsException;
import except.LoginCredentialException;
import except.LoginExistsException;
import except.NotRegisteredException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.DBReaderFactory;
import logic.objects.User;
import logic.objects.message.Request;
import logic.objects.message.Response;
import logic.objects.message.types.RequestType;
import logic.objects.message.types.ResponseType;

/**
 *
 * @author 2dam
 */
public class SThread extends Thread {

    private Socket socket;

    public SThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        ObjectOutputStream write = null;
        ObjectInputStream read = null;
        Response response = null;
        try {
            User user = null;
            //Se abren los flujos de trabajo
            read = new ObjectInputStream(socket.getInputStream());
            write = new ObjectOutputStream(socket.getOutputStream());
            //Se carga el request y se crea la implementacion
            Request request = (Request) read.readObject();
            IDBReader dbReader = DBReaderFactory.getAccess();

            if (request.getRequestType().equals(RequestType.SIGNIN)) {
                user = dbReader.signIn(request.getUser());
            }
            if (request.getRequestType().equals(RequestType.SIGNUP)) {
                user = dbReader.signUp(request.getUser());
            }
            //Mandas los datos al cliente con todo correcto
            response = new Response(user, ResponseType.OK);

        } catch (EmailExistsException e) {
            response = new Response(null, ResponseType.EMAIL_EXISTS_ERROR);
        } catch (LoginExistsException e) {
            response = new Response(null, ResponseType.LOGIN_EXISTS_ERROR);
        } catch (NotRegisteredException e) {
            response = new Response(null, ResponseType.NOT_REGISTERED_ERROR);
        } catch (LoginCredentialException e) {
            response = new Response(null, ResponseType.LOGIN_CREDENTIAL_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                write.writeObject(response);
                write.close();
                read.close();
            } catch (IOException ex) {
                //Logger.getLogger(SThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}

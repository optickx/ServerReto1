package logic;

import except.ServerCapacityException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import logic.model.SThread;
import logic.objects.message.Request;
import logic.objects.message.Response;

public class Controller {
    
    public void run() {
        ServerSocket miServidor = null;
        Socket socket = null;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        Request request = null;
        int cont = 0;
        Response response = null;
        SThread thread = null;

        try {
            
            for (;;) {
                if (cont > 10) {
                    throw new ServerCapacityException();
                }
                miServidor = new ServerSocket(7777);
                socket = miServidor.accept();
                //Lectura de Instruccion en el lado Cliente
                entrada = new ObjectInputStream(socket.getInputStream());
                request = (Request) entrada.readObject();
                System.out.println(request.getRequestType());
                System.out.println(request.getUser().getLogin());
                System.out.println(request.getUser().getPassword());
                cont++;
                thread = new SThread(request);
                thread.start();
            }
        } catch (ServerCapacityException e) {
            //salida = new ObjectOutputStream(socket.getOutputStream());
            
        } catch (Exception e) {
        }
    }
}

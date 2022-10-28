package logic;

import except.ServerCapacityException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.model.SThread;
import logic.objects.message.Request;
import logic.objects.message.Response;

public class Controller {

    private int contadorThreads = 0;

    public synchronized void run() {
        ServerSocket miServidor = null;
        Socket socket = null;

        try {
            for (;;) {
                if (contadorThreads > 10) {
                    throw new ServerCapacityException();
                }
                miServidor = new ServerSocket(7777);
                socket = miServidor.accept();
                createRunThread(socket);
            }
        } catch (ServerCapacityException e) {
            //salida = new ObjectOutputStream(socket.getOutputStream());

        } catch (Exception e) {
        }
    }

    public void createRunThread(Socket socket) {
        //Lectura de Instruccion en el lado Cliente
        try {
            SThread thread = new SThread(socket);
            thread.start();
            contadorThreads++;
        } catch (Exception e) {
        }

    }

    public void closeThread(Socket socket) {
        try {
            //Leemos lo que nos ha mandado el hilo
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            Response response = (Response) entrada.readObject();
            //Escribimos la respuesta para mandarselo al cliente
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            salida.writeObject(response);
            //Cerrar flujos
            salida.close();
            entrada.close();
            contadorThreads--;
        } catch (IOException | ClassNotFoundException ex) {
        }

    }
}

package logic;

import except.ServerCapacityException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import logic.model.SThread;
import logic.objects.message.Response;
import logic.objects.message.types.ResponseType;

public class ControllerThread {

    private static int contadorThreads = 0;

    public void run() throws ServerCapacityException, IOException {
        ServerSocket miServidor = null;
        Socket socket = null;
        miServidor = new ServerSocket(9107);
        for (;;) {
            contadorHilos(contadorThreads, socket);
            socket = miServidor.accept();
            createRunThread(socket);
        }
    }

    public synchronized void contadorHilos(int cont, Socket socket) throws ServerCapacityException {
        if (contadorThreads > 10) {
            try {
                Response response = new Response(null, ResponseType.SERVER_CAPACITY_ERROR);
                ObjectOutputStream write = new ObjectOutputStream(socket.getOutputStream());
                write.writeObject(response);
                //throw new ServerCapacityException();  
            } catch (IOException e) {

            }
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

    public static void closeThread() {
        contadorThreads--;
    }
}

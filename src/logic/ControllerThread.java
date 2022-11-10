package logic;

import except.ServerCapacityException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import logic.model.ExitThread;
import logic.model.SThread;
import logic.objects.message.Response;
import logic.objects.message.types.ResponseType;

public class ControllerThread {

    private static int contadorThreads = 0;
    private final int cantUsers = 10;

    public void run() throws ServerCapacityException, IOException {
        ServerSocket miServidor = null;
        Socket socket = null;
        miServidor = new ServerSocket(9107);
        createExitThread();
        for (;;) {
            contadorHilos(contadorThreads, socket);
            socket = miServidor.accept();
            createRunThread(socket);
        }
    }

    public synchronized void contadorHilos(int cont, Socket socket) {
        if (contadorThreads > cantUsers) {
            try {
                Response response = new Response(null, ResponseType.SERVER_CAPACITY_ERROR);
                ObjectOutputStream write = new ObjectOutputStream(socket.getOutputStream());
                write.writeObject(response);
            } catch (IOException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        }

    }

    public static void closeThread() {
        contadorThreads--;
    }

    private void createExitThread() {
        ExitThread thread = new ExitThread();
        thread.start();
    }
}

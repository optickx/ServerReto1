package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import logic.objects.User;

public class App {

    public static void main(String[] args) throws Exception {
        ServerSocket miServidor = null;
        Socket socket = null;
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        User user = new User();

        miServidor = new ServerSocket(7777);
        socket = miServidor.accept();

        //Lectura de Instruccion en el lado Cliente
        entrada = new ObjectInputStream(socket.getInputStream());
        user = (User) entrada.readObject();
        user.toString();
    }
}

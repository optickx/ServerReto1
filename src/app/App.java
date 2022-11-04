package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import logic.Controller;
import logic.objects.message.Request;

public class App {

    public static void main(String[] args) throws Exception {

        Controller controller = new Controller();
        controller.run();

    }
}

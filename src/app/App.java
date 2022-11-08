package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import logic.ControllerThread;
import logic.objects.message.Request;

public class App {

    public static void main(String[] args) throws Exception {

        ControllerThread controller = new ControllerThread();
        controller.run();

    }
}

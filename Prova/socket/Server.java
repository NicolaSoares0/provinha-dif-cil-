package socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;


public class Server {

    private int port;
    private final Counter counter = new Counter();

    public Server() {
        this.port = 2121;
    }

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        try (ServerSocket server = new ServerSocket(this.port, 2121, InetAddress.getByName("127.0.0.1"))) {

            System.out.println("Servidor iniciado na porta " + this.port);

            while (true) {
                new Thread(new cliente(server.accept(), counter)).start();
            }

        } // Server.close;

    }

}

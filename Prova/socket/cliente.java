package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.w3c.dom.css.Counter;

public class cliente implements Runnable {

    private Socket socket;
    private Counter counter;

    public cliente(Socket socket, socket.Counter counter2) {
        this.socket = socket;
        this.counter = (Counter) counter2;
    }

    @Override
    public void run() {
        //CONECTADO NO SERVER
        try {

            System.out.println(
                    "Novo cliente conectado: " + this.socket.getInetAddress().getHostAddress().toString()
                            + " at port " + this.socket.getPort());

            try (
                    PrintWriter out = new PrintWriter(this.socket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));) {

                String responseBody = "<html><body><h3>\"220 Bem-vindo ao Servidor FTP Simples</h3> " + ((socket.Counter) this.counter).incrementAndGet() + "</p></body></html>";

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html; charset=UTF-8");
                out.println("Content-Length: " + responseBody.length());
                out.println();
                out.println(responseBody);
                out.flush();
            }
            
        } catch (IOException e) {

        } finally {
            this.close();
        }
    }

    private void close() {
        try {
            this.socket.close();
        } catch(IOException e) {
            
        }
    }
}

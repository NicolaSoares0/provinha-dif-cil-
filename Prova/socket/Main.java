package socket;

import java.io.IOException;

public class Main {

    public static void main(String ... args) {
        
        try {

            Server server = new Server(2121);

            server.start();

        } catch (IOException e) {

            System.out.println("Erro ao iniciar o servidor, contate o administrador do sistema: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Erro inesperado: " + e.getMessage() + ". Contate o administrador do sistema.");
        }
    }
    
}

    


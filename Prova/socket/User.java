package socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class User {
    //USUARIO PARA SE CONECTAR NO SERVER
    public static void main(String[] args) throws IOException {
        String server = "2121";
        String username = "albert@domain.com";
        String password = "12345678";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server);
            System.out.println("Conectado ao servidor FTP: " + server);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Falha na conexão com o servidor. Código de resposta: " + replyCode);
                return;
            }
            boolean loggedIn = ftpClient.login(username, password);
            if (loggedIn) {
                System.out.println("230 User logged in " + username);
            } else {
                System.out.println("530 login incorreto.");
                return;
            }

            ftpClient.listFiles();
            //LOGOFF DO SERVER
            ftpClient.logout();
            System.out.println("221 Goodbye.");
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
                System.out.println("Desconectado do servidor FTP.");
            }
        }
    }

@SuppressWarnings("unused")
//variaveis que serão usados no futuro                  
private static void handleLISTCommand(FTPClient ftpClient) throws IOException {
    ServerSocket serverSocket = new ServerSocket(2121); 
    int dataPort = serverSocket.getLocalPort();
    
    String passiveModeResponse = "227 Entering Passive Mode (" +
            "127,0,0,1,PORTA_ALTA,PORTA_BAIXA)" + dataPort / 256 + "," + dataPort % 256 + ")";
    
    System.out.println(passiveModeResponse);
    ftpClient.sendCommand(passiveModeResponse);

    Socket dataSocket = serverSocket.accept();
    System.out.println("Conexão de dados estabelecida na porta: " + dataPort);

    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream()));

    String[] files = {"le1.txt", "le2.txt", "le3.txt"};
    
    for (String file : files) {
        writer.write(file + "\r\n");
    }
    writer.flush();

    writer.close();
    dataSocket.close();
    serverSocket.close();
    System.out.println("Conexão de dados fechada.");

    ftpClient.sendCommand("226 Transfer complete.");
    System.out.println("226 Transfer complete.");
}
}


    

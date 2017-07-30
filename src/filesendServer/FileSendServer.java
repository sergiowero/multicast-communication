import java.net.*;
import java.io.*;

public class FileSendServer {
    public static void main (String args[]) {
        try{
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Listo y esperando clientes");
            while(true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Cliente conectado");
                new ConnectionSend(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        }
    }
}
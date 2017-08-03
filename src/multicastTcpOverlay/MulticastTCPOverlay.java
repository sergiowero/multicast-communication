import java.net.*;
import java.io.*;
import java.util.*;

public class MulticastTCPOverlay {

    private Vector<MulticastTCPConnection> connections;


    public MulticastTCPOverlay(){
        connections = new Vector<MulticastTCPConnection>();
    }

    public void run(){
         try{
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("TCP OVERLAY : Esperando conexiones");
            while(true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("TCP OVERLAY : Conexion recibida");
                AddConnection(new MulticastTCPConnection(this, clientSocket));
            }
        } catch (IOException e) {
            System.out.println("IO :" + e.getMessage());
        }
    }

    public static void main (String args[]) {
       MulticastTCPOverlay overlay = new MulticastTCPOverlay();
       overlay.run();
    }

    public synchronized void SendToAdll(String data){
        for (MulticastTCPConnection conn : connections) {
            conn.Send(data);
        }
    }

    public synchronized void AddConnection(MulticastTCPConnection _connection){
        connections.add(_connection);
    }

    public synchronized void RemoveConnection(MulticastTCPConnection _toRemove){
        connections.remove(_toRemove);
    }
}
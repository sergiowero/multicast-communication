import java.net.*;
import java.io.*;

class MulticastTCPConnection  extends Thread {

    Socket clientSocket;
    MulticastTCPOverlay overlayHost;

    DataInputStream in;
    DataOutputStream out;

    public MulticastTCPConnection (MulticastTCPOverlay aOverlayHost, Socket aClientSocket) {
        clientSocket = aClientSocket;
        overlayHost = aOverlayHost;
        try { 	
            out = new DataOutputStream( clientSocket.getOutputStream());
            in = new DataInputStream( clientSocket.getInputStream());
        } catch(EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO:" + e.getMessage());
        }
    }

    public void run(){
        try { 	 	 
            while(true)
            {
                String str = in.readUTF();
                overlayHost.SendToAdll(str);
            }
        } catch(EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally { 
            try { 
                clientSocket.close();
            } catch (IOException e) {/*close failed*/ }
            overlayHost.RemoveConnection(this);
        }
    }

    public void Send(String data){
        try {
            out.writeUTF(data);
        } catch(IOException e) {
            System.out.println("IO:" + e.getMessage());
        }
    }

}
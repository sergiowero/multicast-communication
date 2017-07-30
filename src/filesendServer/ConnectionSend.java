import java.net.*;
import java.io.*;

class ConnectionSend extends Thread {

    private static final String FILENAME = "testfile.txt";

    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;

    public ConnectionSend (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out = new DataOutputStream( clientSocket.getOutputStream());
            this.start();
        } catch(IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run()
    {
        try { 	 	 
            String data = in.readUTF();
            // Open file
            FileInputStream fileIn = new FileInputStream(FILENAME);

            byte[] buf = new byte[1024];
            int len;

            while ((len = fileIn.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

        } catch(EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally { 
            try { 
                clientSocket.close();
            } 
            catch (IOException e) 
            {/*close failed*/ }
        }
    }
}
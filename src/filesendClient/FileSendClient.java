import java.net.*;
import java.io.*;
public class FileSendClient {

    private static final String FILENAME = "testfile_received.txt";

    public static void main (String args[]) {
        // arguments supply hostname of destination
        Socket s = null;

        try{
            int serverPort = 7896;
            s = new Socket(args[0], serverPort);
            DataInputStream in = new DataInputStream( s.getInputStream());
            DataOutputStream out = new DataOutputStream( s.getOutputStream());
            out.writeUTF("Ready"); // UTF is a string encoding see Sn 4.3

            OutputStream outFile = new FileOutputStream(FILENAME); 
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                outFile.write(buf, 0, len);
            }

            outFile.close();
            out.close();
            in.close();
            
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if(s!=null) 
            try {
                s.close();
            } catch (IOException e) {
                System.out.println("close:" + e.getMessage());
            }
        }
    }
}
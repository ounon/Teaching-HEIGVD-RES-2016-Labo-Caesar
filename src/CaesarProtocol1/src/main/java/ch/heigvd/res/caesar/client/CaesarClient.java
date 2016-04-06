package ch.heigvd.res.caesar.client;

import ch.heigvd.res.caesar.protocol.Protocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ch.heigvd.res.caesar.protocol.Protocol;
import ch.heigvd.res.caesar.filter.CaesarEncryption;

/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class CaesarClient {
    
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    boolean connected = false;
    String userName;

  private static final Logger LOG = Logger.getLogger(CaesarClient.class.getName());
  int port;
  
  CaesarClient(int port){
      this.port = port;
  }
  
  public void connect(String serverAddress, int serverPort, String userName) throws IOException {
		try {
			clientSocket = new Socket(serverAddress, serverPort);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			connected = true;
			this.userName = userName;
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Unable to connect to server: {0}", e.getMessage());
			//cleanup();
			return;
		}
		// Let us start a thread, so that we can listen for server notifications
		//new Thread(new NotificationListener()).start();
		
		// Let us send the HELLO command to inform the server about who the user
		// is. Other clients will be notified.
                String delta = in.readLine();
		//out.println("HELLO " + userName);
                 LOG.log(Level.INFO, "client msg (clear): {0}", "msg");
                out.println(CaesarEncryption.encrypt("msg", Integer.parseInt(delta)));
                
		out.flush();
	}
  

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws IOException {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Client > %5$s%n");
    LOG.info("Caesar client starting...");
    //LOG.info("Protocol constant: " + Protocol.A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER);
    //LOG.info("delta");
    CaesarClient client = new CaesarClient(2222);
    client.connect("localhost", 2222,"ibrahim");
  }
  
}

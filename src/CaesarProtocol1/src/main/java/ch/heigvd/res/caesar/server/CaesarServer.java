package ch.heigvd.res.caesar.server;

import ch.heigvd.res.caesar.client.*;
import ch.heigvd.res.caesar.protocol.Protocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import ch.heigvd.res.caesar.filter.CaesarEncryption;

/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class CaesarServer {

  private static final Logger LOG = Logger.getLogger(CaesarServer.class.getName());
  int port;
  
  public CaesarServer(int port){
      this.port = port;
  }
  public void serveClients() {
		ServerSocket serverSocket;
		Socket clientSocket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException ex) {
			LOG.log(Level.SEVERE, null, ex);
			return;
		}
				
		while (true) {
			try {
				
				LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);				
				clientSocket = serverSocket.accept();
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream());
				String line;
				boolean shouldRun = true;
                                Random rand = new Random();
                                int delta = rand.nextInt(26);
                                out.println(delta);
				//out.println("Welcome to the Single-Threaded Server.\nSend me text lines and conclude with the BYE command.");
				out.flush();
                                
				LOG.info("Reading until client sends BYE or closes the connection...");				
				while ( (shouldRun) && (line = in.readLine()) != null ) {
					if (line.equalsIgnoreCase("bye")) {
						shouldRun = false;
					}
					//out.println("> " + line);
                        	 
                                        if (line.indexOf(Protocol.MSG) == 0){
                                            LOG.log(Level.INFO, "client msg (crypted): {0}", line);
                                           String msg = CaesarEncryption.decrypt(line,delta);
                                            LOG.log(Level.INFO, "client msg (clear): {0}", msg);
                                            out.println(CaesarEncryption.encrypt(msg, delta));
                                        }
					out.flush();
				}
				
				LOG.info("Cleaning up resources...");				
				clientSocket.close();
				in.close();
				out.close();
				
			} catch (IOException ex) {
				if (in != null) {
					try {
						in.close();
					} catch (IOException ex1) {
						LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
					}
				}
				if (out != null) {
					out.close();
				}
				if (clientSocket != null) {
					try {
						clientSocket.close();
					} catch (IOException ex1) {
						LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
					}
				}
				LOG.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}	
	}

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Server > %5$s%n");
    LOG.info("Caesar server starting...");
    LOG.info("Protocol constant: " + Protocol.A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER);
    CaesarServer server = new CaesarServer(2222);
    server.serveClients();
  }
  
}

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server4 {

	private static ServerSocket serverSocketS4 = null;
	private static Socket clientSocketS4 = null;

	  private static final int maxClientsNumberS4 = 20;
	  private static final ClientHandler[] clientsThreadsS4= new ClientHandler[maxClientsNumberS4+1]; 

	  public static void main(String args[]) {
		  
	    try {
	    	serverSocketS4 = new ServerSocket(6040);
	    } catch (IOException e) {
	    	System.out.println(e);
	    }
	    
	    while (true) {
	      try {
	        clientSocketS4 = serverSocketS4.accept();
	        int i;
	        for (i = 0; i < maxClientsNumberS4; i++) {
	          if (clientsThreadsS4[i] == null) 
	          {
	        	  ClientHandler c= new ClientHandler(clientSocketS4, clientsThreadsS4);
	        	  clientsThreadsS4[i]=c;
	        	  clientsThreadsS4[i].start();
	            break;
	          }
	        }
	        if (i == maxClientsNumberS4) {
	          PrintStream outputStream = new PrintStream(clientSocketS4.getOutputStream());
	          outputStream.println("Max clients for this server is reached now. Please try again later");
	          outputStream.close();
	          clientSocketS4.close();
	        }
	      } catch (IOException e) {
	        System.out.println(e);
	      }
	    }
	  }
}

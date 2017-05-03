import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server2{
	private static ServerSocket serverSocketS2 = null;
	private static Socket clientSocketS2 = null;

	  private static final int maxClientsNumber = 20;
	  private static final ClientHandler[] clientsThreads= new ClientHandler[maxClientsNumber+1]; 

	  public static void main(String args[]) {
		  
	    try {
	    	serverSocketS2 = new ServerSocket(6020);
	    } catch (IOException e) {
	    	System.out.println(e);
	    }
	    while (true) {
	      try {
	        clientSocketS2 = serverSocketS2.accept();
	        int i;
	        for (i = 0; i < maxClientsNumber; i++) {
	          if (clientsThreads[i] == null) 
	          {
	        	  ClientHandler c= new ClientHandler(clientSocketS2, clientsThreads);
	        	  clientsThreads[i]=c;
	        	  clientsThreads[i].start();
	            break;
	          }
	        }
	        if (i == maxClientsNumber) {
	          PrintStream outputStream = new PrintStream(clientSocketS2.getOutputStream());
	          outputStream.println("Max clients for this server is reached now. Please try again later");
	          outputStream.close();
	          clientSocketS2.close();
	        }
	      } catch (IOException e) {
	        System.out.println(e);
	      }
	    }
	  }
}

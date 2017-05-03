import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server3 {
	private static ServerSocket serverSocketS3 = null;
	private static Socket clientSocketS3 = null;

	  private static final int maxClientsNumberS3 = 20;
	  private static final ClientHandler[] clientsThreadsS3= new ClientHandler[maxClientsNumberS3+1]; 

	  public static void main(String args[]) {
		  
	    try {
	    	serverSocketS3 = new ServerSocket(6030);
	    } catch (IOException e) {
	    	System.out.println(e);
	    }
	    
	    while (true) {
	      try {
	        clientSocketS3 = serverSocketS3.accept();
	        int i;
	        for (i = 0; i < maxClientsNumberS3; i++) {
	          if (clientsThreadsS3[i] == null) 
	          {
	        	  ClientHandler c= new ClientHandler(clientSocketS3, clientsThreadsS3);
	        	  clientsThreadsS3[i]=c;
	        	  clientsThreadsS3[i].start();
	            break;
	          }
	        }
	        if (i == maxClientsNumberS3) {
	          PrintStream outputStream = new PrintStream(clientSocketS3.getOutputStream());
	          outputStream.println("Max clients for this server is reached now. Please try again later");
	          outputStream.close();
	          clientSocketS3.close();
	        }
	      } catch (IOException e) {
	        System.out.println(e);
	      }
	    }
	  }

}

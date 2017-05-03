//import ClientHandler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;
import java.net.UnknownHostException;


public class Server1{

	private static ServerSocket serverSocketS1 = null;
	private static Socket clientSocketS1 = null;

	  private static final int maxClientsNumberS1 = 20;
	  private static final ClientHandler[] clientsThreadsS1= new ClientHandler[maxClientsNumberS1+1]; 

	  public static void main(String args[]) {
		  
	    try {
	    	serverSocketS1 = new ServerSocket(6010);
	    } catch (IOException e) {
	    	System.out.println(e);
	    }
	    while (true) {
	      try {
	        clientSocketS1 = serverSocketS1.accept();
	        int i;
	        for (i = 0; i < maxClientsNumberS1; i++) {
	          if (clientsThreadsS1[i] == null) 
	          {
	        	  ClientHandler c= new ClientHandler(clientSocketS1, clientsThreadsS1);
	        	  clientsThreadsS1[i]=c;
	        	  clientsThreadsS1[i].start();
	            break;
	          }
	        }
	        if (i == maxClientsNumberS1) {
	          PrintStream outputStream = new PrintStream(clientSocketS1.getOutputStream());
	          outputStream.println("Max clients for this server is reached now. Please try again later");
	          outputStream.close();
	          clientSocketS1.close();
	        }
	      } catch (IOException e) {
	        System.out.println(e);
	      }
	    }
	  }
}


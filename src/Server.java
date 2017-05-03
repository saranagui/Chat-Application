import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server{
		//private static ServerSocket serverSocket = null;
		private static Socket server1ClientSocket=null;
		private static Socket server2ClientSocket=null;
		private static Socket server3ClientSocket=null;
		private static Socket server4ClientSocket=null;


		private static PrintStream sourceOutputStreamS1;
		private static DataInputStream serverInputStreamS1;
		private static PrintStream sourceOutputStreamS2;
		private static DataInputStream serverInputStreamS2;
		private static PrintStream sourceOutputStreamS3;
		private static DataInputStream serverInputStreamS3;
		private static PrintStream sourceOutputStreamS4;
		private static DataInputStream serverInputStreamS4;
		
		private static final PrintStream[] sourceOutputStreams=new PrintStream[4];
		private static final DataInputStream[] serverInputStreams=new DataInputStream[4];
		private static final ServerHandlers[] serverClientHandlers=new ServerHandlers[4];

		  private static final int maxClientsNumber = 20*4+4;
		  private static final ClientHandler[] clientsThreads= new ClientHandler[maxClientsNumber]; 

		  public static void main(String args[]) {
			  try{
				  server1ClientSocket = new Socket("localhost", 6010);
				  sourceOutputStreamS1 = new PrintStream(server1ClientSocket.getOutputStream());
				  serverInputStreamS1 = new DataInputStream(server1ClientSocket.getInputStream());
				  

				  server2ClientSocket = new Socket("localhost", 6020);
				  sourceOutputStreamS2 = new PrintStream(server2ClientSocket.getOutputStream());
				  serverInputStreamS2 = new DataInputStream(server2ClientSocket.getInputStream());
				  

				  server3ClientSocket = new Socket("localhost", 6030);
				  sourceOutputStreamS3 = new PrintStream(server3ClientSocket.getOutputStream());
				  serverInputStreamS3 = new DataInputStream(server3ClientSocket.getInputStream());
				  

				  server4ClientSocket = new Socket("localhost", 6040);
				  sourceOutputStreamS4 = new PrintStream(server4ClientSocket.getOutputStream());
				  serverInputStreamS4 = new DataInputStream(server4ClientSocket.getInputStream());
				
				  sourceOutputStreams[0]=sourceOutputStreamS1;
				  sourceOutputStreams[1]=sourceOutputStreamS2;
				  sourceOutputStreams[2]=sourceOutputStreamS3;
				  sourceOutputStreams[3]=sourceOutputStreamS4;
				  try{
						  String fromUppserServer;
						  while((fromUppserServer=serverInputStreamS1.readLine()) != null&&fromUppserServer.startsWith("Please enter username in this format 'join(username)'"))
						  { 
							  sourceOutputStreams[0].println("join(server)");
						  }
					 }catch (IOException e) {
						 System.err.println("IOException:  " + e);
					 }
				  try{
					  String fromUppserServer;
					  while((fromUppserServer=serverInputStreamS2.readLine()) != null&&fromUppserServer.startsWith("Please enter username in this format 'join(username)'"))
					  { 
						  sourceOutputStreams[1].println("join(server)");
					  }
				 }catch (IOException e) {
					 System.err.println("IOException:  " + e);
				 }
				  try{
					  String fromUppserServer;
					  while((fromUppserServer=serverInputStreamS3.readLine()) != null&&fromUppserServer.startsWith("Please enter username in this format 'join(username)'"))
					  { 
						  sourceOutputStreams[2].println("join(server)");
					  }
				 }catch (IOException e) {
					 System.err.println("IOException:  " + e);
				 }
				  try{
					  String fromUppserServer;
					  while((fromUppserServer=serverInputStreamS4.readLine()) != null&&fromUppserServer.startsWith("Please enter username in this format 'join(username)'"))
					  { 
						  sourceOutputStreams[3].println("join(server)");
					  }
				 }catch (IOException e) {
					 System.err.println("IOException:  " + e);
				 }
				  (new ServerHandlers(server1ClientSocket,sourceOutputStreamS1,sourceOutputStreams)).start();
				  (new ServerHandlers(server2ClientSocket,sourceOutputStreamS2,sourceOutputStreams)).start();
				  (new ServerHandlers(server3ClientSocket,sourceOutputStreamS3,sourceOutputStreams)).start();
				  (new ServerHandlers(server4ClientSocket,sourceOutputStreamS4,sourceOutputStreams)).start();

			    } catch (UnknownHostException e) {
			    	System.err.println("Don't know about host " + "localhost");
			    } catch (IOException e) {
			    	System.err.println("Couldn't get I/O for the connection to the host "
			          + "localhost");
			    }
//			  	try{
//			  		server2ClientSocket = new Socket("localhost", 6020);
//			  		sourceOutputStreamS2 = new PrintStream(server2ClientSocket.getOutputStream());
//			  		serverInputStreamS2 = new DataInputStream(server2ClientSocket.getInputStream());
//					  try{
//						  String fromUppserServer;
//						  while((fromUppserServer=serverInputStreamS2.readLine()) != null&&fromUppserServer.startsWith("Please enter username in this format 'join(username)'"))
//						  { 
//							  //System.out.println("hi i was here");
//							  sourceOutputStreamS2.println("join(server)");
//						  }
//					 }catch (IOException e) {
//						 System.err.println("IOException:  " + e);
//					 }
//				    //new Thread(new Server()).start();
//			    } catch (UnknownHostException e) {
//			    	System.err.println("Don't know about host " + "localhost");
//			    } catch (IOException e) {
//			    	System.err.println("Couldn't get I/O for the connection to the host "
//			          + "localhost");
//			    }
//			  	try{
//			  		server3ClientSocket = new Socket("localhost", 6030);
//			  		sourceOutputStreamS3 = new PrintStream(server3ClientSocket.getOutputStream());
//			  		serverInputStreamS3 = new DataInputStream(server3ClientSocket.getInputStream());
//					  try{
//						  String fromUppserServer;
//						  while((fromUppserServer=serverInputStreamS3.readLine()) != null&&fromUppserServer.startsWith("Please enter username in this format 'join(username)'"))
//						  { 
//							  //System.out.println("hi i was here");
//							  sourceOutputStreamS3.println("join(server)");
//						  }
//					 }catch (IOException e) {
//						 System.err.println("IOException:  " + e);
//					 }
//				    //new Thread(new Server()).start();
//			    } catch (UnknownHostException e) {
//			    	System.err.println("Don't know about host " + "localhost");
//			    } catch (IOException e) {
//			    	System.err.println("Couldn't get I/O for the connection to the host "
//			          + "localhost");
//			    }
//			  	try{
//			  		server4ClientSocket = new Socket("localhost", 6040);
//			  		sourceOutputStreamS4 = new PrintStream(server4ClientSocket.getOutputStream());
//			  		serverInputStreamS4 = new DataInputStream(server4ClientSocket.getInputStream());
//					  try{
//						  String fromUppserServer;
//						  while((fromUppserServer=serverInputStreamS4.readLine()) != null&&fromUppserServer.startsWith("Please enter username in this format 'join(username)'"))
//						  { 
//							  //System.out.println("hi i was here");
//							  sourceOutputStreamS4.println("join(server)");
//						  }
//					 }catch (IOException e) {
//						 System.err.println("IOException:  " + e);
//					 }
//				   // new Thread(new Server()).start();
//			    } catch (UnknownHostException e) {
//			    	System.err.println("Don't know about host " + "localhost");
//			    } catch (IOException e) {
//			    	System.err.println("Couldn't get I/O for the connection to the host "
//			          + "localhost");
//			    }
		  }
			
}


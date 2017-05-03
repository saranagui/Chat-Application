import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class ServerHandlers extends Thread{
	private DataInputStream serverInputStream = null;
	private PrintStream sourceOutputStream = null;
	private Socket serverClientSocket = null;
	private final PrintStream[] sourceOutputStreams;
	private final String[] allClients=new String[20*4];
	private static String source;
	public ServerHandlers(Socket clientSocket, PrintStream output,PrintStream[] serversOutput) {
		    this.serverClientSocket = clientSocket;
		    this.sourceOutputStream=output;
		    this.sourceOutputStreams = serversOutput;
		  }
	@Override
	public void run() {
		String fromServer1=null;
		try {
			serverInputStream = new DataInputStream(serverClientSocket.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	      

	    try {
	    	//System.out.println("hi");
	      while (((fromServer1 = serverInputStream.readLine()) != null&&
	    		 !fromServer1.startsWith("Please enter username in this format 'join(username)'"))){//||

	    	  boolean proceed=false;
			if(fromServer1!=null)
	    		proceed=true;
	    	  if(proceed)//||fromServer1.startsWith("getAllMemberList")))
	    	  {
	    		//  System.out.println(fromServer1);

	    		  if((fromServer1.startsWith("chat")))
	    		  {
	    			  System.out.println(fromServer1);
	    			  int i;
	    			  for(i=0;i<4;i++){
	    				  if(sourceOutputStreams[i]!=null&&sourceOutputStreams[i]!=sourceOutputStream)
	    				  {
	    					  //System.out.println("check");
	    					  sourceOutputStreams[i].println(fromServer1);
	    				  }
	    			  }
	    		  }
	    		  else if((fromServer1.startsWith("getAllMembers")))
	    		  {
	    			  System.out.println(fromServer1);
	    			  int i;
	    			  source=fromServer1.substring(14,fromServer1.length()-1);
	    			  for(i=0;i<4;i++){
	    				  if(sourceOutputStreams[i]!=null)
	    				  {
	    					  //System.out.println("check");
	    					  sourceOutputStreams[i].println("getMemberList()");
	    				  }
	    			  }
	    			  
	    			  
	    		  }
	    		  else if((fromServer1.startsWith("client"))){
	    			  System.out.println(fromServer1);
	    			  for(int i=0;i<4;i++)
	    			  {
	    				  if(sourceOutputStreams[i]!=null&&source!=null&&(!source.equals(fromServer1.substring(8,fromServer1.length()))))
	    				  {
	    					  //System.out.println("check");
	    					  sourceOutputStreams[i].println("chat1("+source+","+fromServer1+",server)");
	    				  }
	    			  }
	    		  }
	    	  }
	      }
	    } catch (IOException e) {
	      System.err.println("IOException:  " + e);
	    }
	}	
		
	
}

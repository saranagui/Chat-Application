import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


public class ClientHandler extends Thread{
	
	private String clientName;
	private DataInputStream inputStream = null;
	private PrintStream outputStream = null;
	private Socket clientSocket = null;
	private final ClientHandler[] clients;
	private int serverCapacity;
	//private final ClientHandler[] servers=new ClientHandler[2];
	private DataOutputStream outputStreamBytes;


	public ClientHandler(Socket clientSocket, ClientHandler[] clients) {
		  this.clientSocket = clientSocket;
		  this.clients = clients;
		  serverCapacity = clients.length;
	  }


	  public void run() {
	    try {
	      inputStream = new DataInputStream(clientSocket.getInputStream());
	      outputStream = new PrintStream(clientSocket.getOutputStream());
  		  outputStreamBytes = new DataOutputStream(clientSocket.getOutputStream());
	      
	      //outputStream.println("input: ");
    	  System.out.println("here");

	      String nameLine="";
	      boolean joined=false;
	      while(!nameLine.startsWith("join(")||!joined){
		      outputStream.println("Please enter username in this format 'join(username)'.");
		      nameLine = inputStream.readLine();
		      if(nameLine!=null&&nameLine.startsWith("join("))
		      {	  
		    	System.out.println("here1");
		    	String name=nameLine.substring(5,nameLine.length()-1);
		      	joined=joinResponse(name);
		      
		      }
	      }
	      while (true) {
	        String line = inputStream.readLine();
	        if (line.startsWith("chat1(")||line.startsWith("chat2(")||line.startsWith("chat0(")) {
	        	String[] message = line.split(",");
	            if (message.length > 2 && message[1] != null) {
		          String destination=message[0].substring(6);
		          int TTL=Integer.parseInt(""+message[0].charAt(4));
		          String actualMessage=message[1];
		          String source=message[2].substring(0,message[2].length()-1);;
              	  route(destination,actualMessage,TTL,source);
	            }
	            else{
		        	this.outputStream.println("Can not read input. Make sure you used the right fomat: 'chat(username,message)' ;)");
	            }
	        }
	        if (line.startsWith("chat(")) {
	        	String[] message = line.split(",");
	            if (message.length > 1 && message[1] != null) {
		          String source=this.clientName; 
		          String destination=message[0].substring(5);
		          int TTL=2;
		          String actualMessage=message[1].substring(0,message[1].length()-1);
              	  route(destination,actualMessage,TTL,source);
	            }
	            else{
		        	this.outputStream.println("Can not read input. Make sure you used the right fomat: 'chat(username,message)' ;)");
	            }
	        }
	        else if(line.startsWith("getMemberList()")){
	        	getMemberList();
	        }

	        else if(line.startsWith("getAllMembers()")){
	        	getAllMembers(this.getClientName());
	        }
//	        else if(line.startsWith("getAllMembers")){
//		        //int TTL=Integer.parseInt(""+line.charAt(13));
//		        //String[]message=line.split("(");
//		        String source=line.substring(15, line.length()-1);
//	        	getAllMembers(source);
//	        }
	        else if (line.compareToIgnoreCase("quit")==0||line.compareToIgnoreCase("bye")==0) {
		        quit();
		    }
//	        else if (line.startsWith("server(")) {
//	        	String[] message=line.split(",");
//	        	if (message.length > 2 && message[1] != null && message[2]!=null){
//	        	String source=message[0].substring(7, message[0].length());
//	        	int TTL=Integer.parseInt(message[1]);
//	        	String actualMessage=message[2];
//				displayMessageToSource(source,TTL,actualMessage);
//	        	//goBackToSource();
//	        	}
//	        }
	        else{
	        	this.outputStream.println("Can not read input. Make sure you used one of the offered commands described above ;)");
	        }
	      }
	    }catch (IOException e) 
	    {
	  	}
	 }

	private void displayMessageToSource(String source,int TTL, String actualMessage) {
		if(this.clientName.equals(source))
		{
			this.outputStream.println(actualMessage);
			return;
		}

		boolean flag=true;
        synchronized (this) {
	        for (int i = 0; i < serverCapacity; i++) {
	        	if (this.clients[i] != null && this.clients[i] != this
	             		&& this.clients[i].clientName != null
	               		&& this.clients[i].clientName.equals(source)) {
	               this.clients[i].outputStream.println(actualMessage);
	               flag=false;
	               return;
	            }
	        }
	        if(flag)
	        {	//like the server! this is wrong.. keda beyo3od ye loop fe nafs el class!
	            if (this.clientName != null
	            		&&(this.clientName.equals("server1")
	            		||this.clientName.equals("server2")
	                    ||this.clientName.equals("server3")
	                    ||this.clientName.equals("server4"))){
	              	flag=false;
	               	TTL--;
	    			this.outputStream.println("server("+source+","+TTL+","+actualMessage);
	            }
	        }           
        }		
	}

	private void quit() {
		outputStream.println("Bye " + clientName);
	      synchronized(this){
		      for (int i = 0; i < serverCapacity; i++) {
		        if (clients[i] == this) {
		          clients[i] = null;
		          break;
		        }
		      }
	      }
	      try {
			inputStream.close();
			outputStream.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private void getAllMembers(String source) {
		
		for(int j=0;j<serverCapacity;j++)
		{
			if (clients[j] != null && clients[j].clientName !=null && !clients[j].clientName.equals(this.clientName)&& (clients[j].clientName.equals("server")))
			{
				this.clients[j].outputStream.println("getAllMembers"+"("+source+")");
				//this.clients[j].outputStream.println("getAllMembers("+source+")");
			}
		}
	}
	private void OtherMembersFromClientServer(){
		for(int i=0;i<serverCapacity;i++){
			if(clients[i] != null && clients[i].clientName !=null && !clients[i].clientName.equals(this.clientName)&& (clients[i].clientName.equals("server1") 
					||clients[i].clientName.equals("server2")||clients[i].clientName.equals("server3")||clients[i].clientName.equals("server4"))){
				this.clients[i].outputStream.println("getAllMembers()");
			}
		}
	}
	private int modulus(int i) {
		return (i<0)?-1*i:i;
	}

	private void getMemberList() {
		for(int i=0;i<serverCapacity;i++)
		{
			if (clients[i] != null && clients[i].clientName !=null && !clients[i].clientName.equals(this.clientName)&& !clients[i].clientName.equals("server"))
		          this.outputStream.println("client: "+clients[i].clientName);
		}
	}
	
	private boolean joinResponse(String name) {
		boolean flag=true;
		for (int i = 0; i < this.serverCapacity; i++) {
	        if (this.clients[i] != null && this.clients[i].clientName !=null 
	        		&& this.clients[i].clientName.equals(name)) {
	          this.outputStream.println("Sorry this username is already used. "
	          		+ "Please choose another one to join!"); 
	          flag=false;
	        }
		}
		
		if(flag)
		{
			this.clientName=name;
			outputStream.println("Welcome " + this.clientName+ " \nTo leave enter 'quit' "
					+ "or 'bye' in a new line\nTo chat with another user "
					+ "enter 'chat(user,message)'\nTo view online people on this server "
					+ "enter 'getMemberList()'\nTo view all online people enter 'getAllMembers()");
		}
		return flag;
	}

	private void route(String destination, String actualMessage, int TTL, String source) {
		if(TTL<=0)
		{
			this.outputStream.println("Sorry "+destination+" is not online");
			return;
		}
		if (!actualMessage.isEmpty()) 
		{
			boolean flag=true;
	        synchronized (this) {
	            this.outputStream.println(source + ": " + actualMessage);

		        for (int i = 0; i < serverCapacity; i++) {
		        	if (this.clients[i] != null && this.clients[i] != this
		             		&& this.clients[i].clientName != null
		               		&& this.clients[i].clientName.equals(destination)) {
		        		if(!source.equals("server"))
		        			this.clients[i].outputStream.println(source + ": " + actualMessage);
		        		else
		        			this.clients[i].outputStream.println(actualMessage);

		               flag=false;
		               break;
		            }
		        }

        		TTL--;
		        if(flag)
		        {
		            for (int i = 0; i < serverCapacity; i++) {
		            	if (this.clients[i] != null //&& this.clients[i] != this
		                    && this.clients[i].clientName != null
		                    && (this.clients[i].clientName.equals("server"))){
		                		//this.clients[i].outputStream.println(this.clientName + ": " + actualMessage);
		                		flag=false;
		                		this.clients[i].outputStream.println("chat"+TTL+"("+destination+","+actualMessage+","+source+")");
		                		break;
		            	}
		            }
		            if(flag)
		            	this.outputStream.println("Sorry "+destination+" is not online");
		        }
	        }		
		}
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public DataInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(DataInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public PrintStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(PrintStream outputStream) {
		this.outputStream = outputStream;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public int getServerCapacity() {
		return serverCapacity;
	}

	public void setServerCapacity(int serverCapacity) {
		this.serverCapacity = serverCapacity;
	}

	public DataOutputStream getOutputStreamBytes() {
		return outputStreamBytes;
	}

	public void setOutputStreamBytes(DataOutputStream outputStreamBytes) {
		this.outputStreamBytes = outputStreamBytes;
	}

	public ClientHandler[] getClients() {
		return clients;
	}


	
	
}

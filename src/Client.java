import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Client implements Runnable{
	  private static Socket clientSocket = null;
	  private static DataInputStream inputStream = null;
	  private static DataOutputStream outputStream = null;
	  private static boolean closed = false;
	  private static BufferedReader inFromUser = null;
	//  private static Thread Clienthandler;
	  
	  //private String messageto;

  	//FIRST WINDOW
	private JFrame frame;	
	private final JLabel lblEnterYourName = new JLabel("Enter your name and click Join Chat");
	private final JTextField txtName = new JTextField();
	private final JButton btnJoinChat = new JButton("Join Chat");
	private final JButton submitName = new JButton("Submit Name");

	
	//SECOND WINDOW
	private JFrame frame2;
	private JTextField textField;
	//private JButton submitName = new JButton("Submit Name");
	private JTextField textField_1;
	//private JButton submitName = new JButton("Submit Name");
	private JTextField textField_2;
	//private JButton submitName = new JButton("Submit Name");
	private JLabel lblMessageReceivedFrom;
		
	//THIRD WINDOW
	private JFrame frame3;
	private JTextField textField3;
	
	public Client(){
		initialize();
		frame.setVisible(true);
		}
	  
	  public static void main(String[] args) {

	    try {
	    	
	      clientSocket = new Socket("localhost", 6010);
	      outputStream = new DataOutputStream(clientSocket.getOutputStream());
	      inputStream = new DataInputStream(clientSocket.getInputStream());
	      inFromUser = new BufferedReader(new InputStreamReader(System.in));
	      
	    } catch (UnknownHostException e) {
	      System.err.println("Don't know about host " + "localhost");
	    } catch (IOException e) {
	      System.err.println("Couldn't get I/O for the connection to the host "
	          + "localhost");
	    }
	    if (clientSocket != null && inputStream != null) {
//	      try {
	    	new Thread(new Client()).start();
	    	
//	        while (!closed) {
//	        	String sentence;
//	        	sentence = inFromUser.readLine();
//	        	if(sentence!=null)
//	        		outputStream.writeBytes(sentence + '\n');
//	        }
//	        inputStream.close();
//	        clientSocket.close();
//	      } catch (IOException e) {
//	        System.err.println("IOException:  " + e);
//	      }
	    }
	  }
	  public void run() {
		    String fromServer;
		    try {
		    	
		      while (inputStream!=null&&(fromServer = inputStream.readLine()) != null) {
			        System.out.println(fromServer);

		    	  if(fromServer.startsWith("client")){
		    		  textField3.setText(textField3.getText()+"\n"+fromServer);
		    		  frame3.setVisible(true);
		    		  frame2.setVisible(false);
		    	  }else if(fromServer.equals("Sorry this username is already used. "
			          		+ "Please choose another one to join!")){
		    		  lblEnterYourName.setText(fromServer);
		    		  frame.validate();
		    		  frame.repaint();
		    		  frame.setVisible(true);
		    		  frame2.setVisible(false);
		    	  }else if(fromServer.startsWith("Sorry ")&&fromServer.endsWith(" is not online")){
		    		  lblMessageReceivedFrom.setText(lblMessageReceivedFrom.getText()+"\n"+ fromServer);
		    		  textField_2.setText(lblMessageReceivedFrom.getText());
		    		  frame2.validate();
		    		  frame2.repaint();
		    	  }else if(fromServer.startsWith("Welcome ")){/*&&fromServer.endsWith(" \nTo leave enter 'quit' "
							+ "or 'bye' in a new line\nTo chat with another user "
							+ "enter 'chat(user,message)'\nTo view online people on this server "
							+ "enter 'getMemberList()'\nTo view all online people enter 'getAllMembers()")){*/
		    		  frame2.validate();
		    		  frame2.repaint();
		    		  frame.setVisible(false);
		    		  frame2.setVisible(true);
	    		  }else if(fromServer.equals("Please enter username in this format 'join(username)'.")){
	    				System.out.println("I was here");
	    		  }else if (fromServer.startsWith("Bye"))
			      {	
			        	lblMessageReceivedFrom.setText(fromServer);
			    		textField_2.setText(lblMessageReceivedFrom.getText());
			        	frame2.validate();
			        	frame2.repaint();
			        	quit();
			      }
	    		  else
	    		  {
	    			  lblMessageReceivedFrom.setText(fromServer);
	    			  textField_2.setText(lblMessageReceivedFrom.getText());
		    		  frame2.validate();
		    		  frame2.repaint();
	    		  }
		    	  
		      }
		    } catch (IOException e) {
		      System.err.println("IOException:  " + e);
		    }
		  }


	private void quit() {
		closed = true;
        try {
        	frame.dispose();
        	frame2.dispose();
        	frame3.dispose();
			inputStream.close();
	        clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		//FRAME THREE
		frame3 = new JFrame();
		frame3.setBounds(100, 100, 585, 408);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.getContentPane().setLayout(null);
		
		textField3 = new JTextField();
		textField3.setEditable(false);
		textField3.setBounds(279, 55, 216, 257);
		frame3.getContentPane().add(textField3);
		textField3.setColumns(10);
		
		JLabel lblListOfThe = new JLabel("List of the Members :");
		lblListOfThe.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblListOfThe.setBounds(27, 26, 181, 49);
		frame3.getContentPane().add(lblListOfThe);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnBack.setBounds(57, 316, 89, 42);
		frame3.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2.setVisible(true);
				frame3.setVisible(false);
			}
		});
		
		JButton btnQuit2 = new JButton("Quit");
		btnQuit2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnQuit2.setBounds(168, 316, 89, 42);
		frame3.getContentPane().add(btnQuit2);
		btnQuit2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					outputStream.writeBytes("quit\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//FRAME TWO
		{{
		frame2 = new JFrame();
		frame2.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 19));
		frame2.setBounds(100, 100, 585, 408);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Get Member List");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnNewButton.setBounds(10, 11, 200, 50);
		frame2.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					outputStream.writeBytes("getMemberList()\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnGetAllMembers = new JButton("Get All Members");
		btnGetAllMembers.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnGetAllMembers.setBounds(227, 11, 200, 50);
		frame2.getContentPane().add(btnGetAllMembers);
		btnGetAllMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					outputStream.writeBytes("getAllMembers()\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		textField = new JTextField();
		textField.setBounds(10, 149, 417, 74);
		frame2.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 97, 215, 25);
		frame2.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblChooseAMember = new JLabel("Type your message.");
		lblChooseAMember.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChooseAMember.setBounds(10, 114, 137, 43);
		frame2.getContentPane().add(lblChooseAMember);
		
		JLabel lblChooseAMember_1 = new JLabel("Choose a member to send a message to.");
		lblChooseAMember_1.setBounds(10, 71, 265, 14);
		frame2.getContentPane().add(lblChooseAMember_1);

		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSend.setBounds(437, 157, 116, 50);
		frame2.getContentPane().add(btnSend);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String user = textField_1.getText();
					String message = textField.getText();
					outputStream.writeBytes("chat("+ user+ ","+ message+ ")\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnQuit = new JButton("QUIT");
		btnQuit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnQuit.setBounds(470, 11, 89, 23);
		frame2.getContentPane().add(btnQuit);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					outputStream.writeBytes("quit\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		lblMessageReceivedFrom = new JLabel("Message received from : ");
		lblMessageReceivedFrom.setBounds(20, 244, 158, 25);
		
		
		textField_2 = new JTextField();
		textField_2.setText(lblMessageReceivedFrom.getText());
		textField_2.setEditable(false);
		textField_2.setBounds(10, 280, 417, 78);
		frame2.getContentPane().add(textField_2);
		textField_2.setColumns(10);
	}}
		//FRAME ONE
		{{
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setBounds(172, 103, 89, 20);
		txtName.setColumns(10);
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		lblEnterYourName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterYourName.setBounds(96, 33, 240, 14);

		frame.getContentPane().add(submitName);
		submitName.setVisible(true);
		submitName.setBounds(172, 167, 89, 23);
		frame.getContentPane().add(lblEnterYourName);
		
		
		frame.getContentPane().add(txtName);
		//String name= "";
		btnJoinChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					outputStream.writeBytes("join("+txtName.getText() +")\n");
					System.out.println(txtName.getText());

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnJoinChat.setBounds(172, 167, 89, 23);
		
		frame.getContentPane().add(btnJoinChat);
		btnJoinChat.setVisible(false);
	
		submitName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtName.getText()!=null)
				{
					txtName.setEditable(false);
					submitName.setVisible(false);
					btnJoinChat.setVisible(true);
				}
			}
		});
		

		}}
		
		
	}
}

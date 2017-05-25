package server;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class ConHandler  
{
	private Socket sock;
	private Scanner input;
	private ArrayList<Socket> conArray = new ArrayList<Socket>();
	private ArrayList<String> unameArray = new ArrayList<String>();
	private RCV_Thread cThread;
	
	public ConHandler()
	{
		// This class requires a socket as an argument
		System.err.println("The ConHandler class requires a socket");
		throw new IllegalArgumentException();		
	}
	
	public ConHandler(ServerSocket s)
	{
		System.out.println("[+] in Conn_T - constructor");
		
		try
		{
			// Initialize private fields
			sock = s;
			input = new Scanner(sock.getInputStream());
			cThread = new RCV_Thread();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	public int execute()
	{
		System.out.println("[+] In Conn_T - method execute");
		
		// Create and start the receive thread
		Thread x = new Thread(cThread);
		x.start();
		
		//TODO 
		// add socket to ArrayList
		// add user to ArrayList
		
		return 0;
	}
	
	private void send(String str)
	{
		//TODO
		// pause the receive thread
		// send the string
		// restart the receive thread
	}
	
	private class RCV_Thread implements Runnable
	{
		
		public void run() 
		{
			System.out.println("[+] In Conn_T - private class Con_Thread - method run");
			
			//TODO
			// infinite loop for input Scanner
			// 
			return;
		}//end method run
	}
}//end class Conn_T

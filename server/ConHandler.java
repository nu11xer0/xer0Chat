package server;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class ConHandler  
{
	private ServerSocket srvSock;
	private Socket tempSock;
	private ArrayList<Socket> conArray = new ArrayList<Socket>();
	private ArrayList<String> unameArray = new ArrayList<String>();
	private RCV_Thread cThread;
	
	public ConHandler()
	{
		// This class requires a socket as an argument
		System.err.println("[!] The ConHandler class requires a socket");
		throw new IllegalArgumentException();		
	}
	
	public ConHandler(ServerSocket s)
	{
		System.out.println("[+] in Conn_T - constructor");
		
		// Initialize private fields
		srvSock = s;		
		cThread = new RCV_Thread();		
	}
	
	public int execute()
	{
		System.out.println("[+] In Conn_T - method execute");
		
		while(true)
		{
			try 
			{
				tempSock = srvSock.accept();			
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return 1;
			}
			
			// Create and start the receive thread
			Thread x = new Thread(cThread);
			x.start();
			
			conArray.add(tempSock);
		
			//TODO 
			// add socket to ArrayList
			// add user to ArrayList
		}
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
		private Scanner input;
		
		public RCV_Thread()
		{
			// This class requires a socket as an argument
			System.err.println("[!] The RCV_Thread class requires a Scanner object");
			throw new IllegalArgumentException();		
		}
		
		public RCV_Thread(Scanner in)
		{
				
			try 
			{
				this.input = in;
				input = new Scanner(tempSock.getInputStream());
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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

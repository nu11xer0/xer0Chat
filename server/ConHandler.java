package server;

/*
 *  FIXIT NOTES
 * null pointer issue in RCV_Thread
 * -full rework of threads and well... fucking everything.. sigh.
 * */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class ConHandler  
{
	private final static int PORT = 4444;
	private ServerSocket srvSock;
	private Socket socket;	
	private Scanner input;
	private ArrayList<Socket> conArray = new ArrayList<Socket>();
	private ArrayList<String> unameArray = new ArrayList<String>();
	private LST_Thread lThread;
	private RCV_Thread cThread;
	
	public ConHandler() throws IOException
	{
		System.out.println("[+] in Conn_T - constructor");
		
		srvSock = new ServerSocket(PORT);
		
		// Create and start the listen thread
		lThread = new LST_Thread();		
		Thread x = new Thread(lThread);
		x.start();
		
			
		// Create and start the receive thread
		cThread = new RCV_Thread();
		Thread y = new Thread(cThread);
		y.start();		
	}	
	
	private class LST_Thread implements Runnable
	{
		public Thread x;
		private Socket tempSock;
		
		public void run() 
		{			
			do{			
				try 
				{
					tempSock = srvSock.accept();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}								
							
				conArray.add(tempSock);
			
				//TODO 
				// add socket to ArrayList 
				// add user to ArrayList
				// find a graceful way to shutdown.				
			}while(true);			
		}
		
		private void sendUserList(ArrayList<String> users)
		{
			//TODO
			// pause the receive thread
			// send the list
			// restart the receive thread
			try 
			{
				x.wait();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0; i < conArray.size(); i++)
			{
				// TODO Send user list 
			}
			
			notify();
		}
	}
	
	private class RCV_Thread implements Runnable
	{				
		public RCV_Thread()
		{				
			try 
			{
				//this.input = new Scanner(PrintWriter(tempSock));
				input = new Scanner(socket.getInputStream());
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() 
		{
			System.out.println("[+] In \"ConnHandler\" private class \"RCV_Thread\" method \"run\"");
			
			//TODO
			// infinite loop for input Scanner
			// 
			return;
		}//end method run
	}
}//end class Conn_T

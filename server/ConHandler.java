package server;

/*
 *  FIXIT NOTES
 * null pointer issue in RCV_Thread
 * -full rework of threads and well... fucking everything.. sigh.
 * */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class ConHandler  
{
	private final static int PORT = 4444;
	private ServerSocket srvSock;
	//private Socket socket;	
	private Scanner input;
	private ArrayList<Socket> conArray = new ArrayList<Socket>();
	private ArrayList<String> unameArray = new ArrayList<String>();
	private LST_Thread lThread;
	private RCV_Thread cThread;
	private Thread x;
	
	public ConHandler() throws IOException
	{
		System.out.println("[+] in ConHandler - constructor");
		
		srvSock = new ServerSocket(PORT);
				
		// Create and start the listen thread
		lThread = new LST_Thread();		
		x = new Thread(lThread);
		x.start();				
	}	
		
	private class LST_Thread implements Runnable
	{
		public Thread y;
		private Socket tempSock;
		private volatile Boolean run = true;
		
		public void run() 
		{			
			System.out.println("[+] in \"ConHandler\" private class \"LST_Thread\" run method");
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
				
				// Create and start the receive thread
				cThread = new RCV_Thread(tempSock);
				Thread y = new Thread(cThread);
				y.start();
				
				//TODO 
				// add user to ArrayList
				// find a graceful way to shutdown.				
			}while(run);			
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
			
			x.notify();
		}
	}
	
	private class RCV_Thread implements Runnable
	{		
		//private volatile Boolean run;
		
		public RCV_Thread(Socket s)
		{				
			try 
			{
				//this.input = new Scanner(PrintWriter(tempSock));
				input = new Scanner(s.getInputStream());
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
			
			while(true)
			{
				if(input.hasNext())
				{
					String tempStr = input.nextLine();
					
					for(int i = 0; i < conArray.size() - 1; i++)
					{
						try 
						{
							PrintWriter out = new PrintWriter(conArray.get(i).getOutputStream());
							out.print("You said: ");
							out.println(tempStr);
							out.flush();
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		}//end method run		
	}
}//end class Conn_T

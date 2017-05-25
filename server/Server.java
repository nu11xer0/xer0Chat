package server;

/* Project: xer0Chat
 * Author:  nu11xer0
 * Date:	25 May 2017
 * Purpose: A simple irc-like chat server
 */

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;


public class Server 
{		
	private final static int PORT = 4444;
	private static Conn_T conThread;
	private static Socket s;
	private static ServerSocket ss;

	public static void main(String[] args) 
	{
		Boolean gogogo = true;
		
		try 
		{	
			ss = new ServerSocket(PORT);		
		
			do{							
				s = ss.accept();								
				conThread = new Conn_T(s);	
				
				//TODO: add socket to the ConArray
				//		handle user name 
				//		start thread
				//		find a graceful way to shutdown.
				
			}while(gogogo);
		} 
		catch (IOException e) 
		{
			//TODO make this more robust
			e.printStackTrace();
		}
		return;
	}//end method main
}

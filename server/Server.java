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
	private static ConHandler handler;
	private static ServerSocket ss;

	public static void main(String[] args) 
	{
		Boolean gogogo = true;
		
		try 
		{
			//TODO
			// This needs to be reworked and moved into the handlers class 
			ss = new ServerSocket(PORT);		
		
			do{							
			    Socket s = ss.accept();								
				handler = new ConHandler(ss);	
				if(handler.execute() != 0)
				{
					System.err.println("[!] Server handler failed or died. Terminating.");
					if(s.isConnected())
					{ s.close(); }
					if(!ss.isClosed())
					{ ss.close(); }
				}
				//TODO: find a graceful way to shutdown.				
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

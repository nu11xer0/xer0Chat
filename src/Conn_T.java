package src;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Conn_T  
{
	private Socket sock;
	private Scanner input;
	private ArrayList<Socket> conArray = new ArrayList<Socket>();
	private ArrayList<String> unameArray = new ArrayList<String>();
	protected Conn_Thread cThread;
	
	public Conn_T()
	{
		throw new IllegalArgumentException();
	}
	
	public Conn_T(Socket s)
	{
		System.out.println("[+] in Conn_T - constructor");
		
		try
		{
			sock = s;
			input = new Scanner(sock.getInputStream());
			cThread = new Conn_Thread();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	public int execute()
	{
		System.out.println("[+] In Conn_T - method execute");
		
		return 0;
	}
	
	private class Conn_Thread implements Runnable
	{
		public void run() 
		{
			System.out.println("[+] In Conn_T - private class Con_Thread - method run");
			
			return;
		}//end method run
	}
}//end class Conn_T

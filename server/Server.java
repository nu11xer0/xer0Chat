package server;

/* Project: xer0Chat
 * Author:  nu11xer0
 * Date:	25 May 2017
 * Purpose: A simple irc-like chat server
 */

import java.io.IOException;
import java.util.Scanner;


public class Server 
{		
	
	private static ConHandler handler;
	

	public static void main(String[] args) 
	{
		Scanner consoleInput = new Scanner(System.in);
		int choice = 0;
		
		//System.out.println("[+] In class \"Server\" method \"main\"");
		
		try 
		{ handler = new ConHandler(); }
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}		
		
		while(true)
		{
			printMenu();
			choice = consoleInput.nextInt();
			
			switch(choice)
			{
				case 99: System.out.println("[+] Exiting...");
						 try 
						 { handler.killSock(); } 
						 catch (IOException e) 
						 {	e.printStackTrace(); }
						 consoleInput.close();
						 System.exit(0); 
						 break;
						 
				default: System.out.println("[+] Ass sphincter says \"What\"?");
						 break;
			}
		}		
	}//end method main
	
	public static void printMenu()
	{
		System.out.printf("%s%n%n%s%n%n%s",
						  "_-= xeroChat Server Console =-_",
						  "type 99 <enter> to quit.",
						  "> ");		
		return;
	}//end method printMenu
}

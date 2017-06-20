package server;

/* Project: xer0Chat
 * Author:  nu11xer0
 * Date:	25 May 2017
 * Purpose: A simple irc-like chat server
 */

import java.util.Scanner;


public class Server 
{		
	
	private static ConHandler handler;
	

	public static void main(String[] args) 
	{
		Scanner consoleInput = new Scanner(System.in);
		int choice = 0;
		Boolean gogogo = true;
		
		System.out.println("[+] In class \"Server\" method \"main\"");
		
		try 
		{
			//TODO
			// This needs to be reworked and moved into the handlers class 
			handler = new ConHandler();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}		
		
		while(gogogo)
		{
			printMenu();
			choice = consoleInput.nextInt();
			
			switch(choice)
			{
				case 99: System.out.println("[+] Exiting...");
						 System.exit(0); 
						 //gogogo = false;
						 //handler.shutdown();
						 break;
				default: System.out.println("[+] Ass sphincter says \"What\"?");
						 break;
			}
		}
		
		consoleInput.close();
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

package server;

/*
 *  FIXIT NOTES
 * null pointer issue in RCV_Thread *fixed
 * IndexOutOfBoundsException in RCV_Thread:123 *fixed
 * 
 * */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class ConHandler{
	private final static int PORT = 4444;
	private ServerSocket srvSock;
	private ArrayList<Socket> conArray = new ArrayList<Socket>(); 
	private ArrayList<String> unameArray = new ArrayList<String>(); //not implemented yet...
	private LST_Thread lThread;	
	private Thread x;
	
	public ConHandler() throws IOException{		
		srvSock = new ServerSocket(PORT);
				
		// Create and start the listen thread
		lThread = new LST_Thread();		
		x = new Thread(lThread);
		x.start();	
	}	
	
	public Boolean killSock() throws IOException{
		if(!srvSock.isClosed()){
			for(int i = 0; i < conArray.size(); i++){
				conArray.get(i).close();
			}
			
			srvSock.close();
			System.out.println("\n[+] Server Socket closed.");
			return(true);
		}
		else{
			System.err.println("\n[!] Server Socket already closed.");
			return(false);
		}
	}
		
	private class LST_Thread implements Runnable{
		private Socket tempSock;
		private Scanner in;
		
		public void run(){			
			//System.out.println("[+] in \"ConHandler\" private class \"LST_Thread\" run method");
			
			while(true){					
				try{
					tempSock = srvSock.accept();
					conArray.add(tempSock);				
					
					PrintWriter out = new PrintWriter(tempSock.getOutputStream());
					in = new Scanner(tempSock.getInputStream());
					String uname = null;
					
					out.print("Enter a user name (10 char max)\n\r> ");
					out.flush();
					
					while(uname == null){
						if(in.hasNext()){
							uname = in.nextLine();
							
							if(uname.length() > 10){
								unameArray.add(uname.substring(0,9));
							}
							else{
								unameArray.add(uname);
							}
							
							//send unameArray to all connected clients
							for(int i = 0; i < conArray.size(); i++){
								if(!conArray.get(i).isClosed()){
									try {
										PrintWriter outTemp = new PrintWriter(conArray.get(i).getOutputStream());
										
										for(int x = 0; x < unameArray.size(); x++){
											outTemp.println(unameArray.get(x));									
											outTemp.flush();
										}
									} 
									catch (IOException e){
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								else{
									conArray.remove(i);
									unameArray.remove(i);
								}
							}
						}
					}
					
					//
				} 
				catch (IOException e){
					e.printStackTrace();
				}	
				
				// Create and start the receive thread
				RCV_Thread cThread = new RCV_Thread(tempSock);
				Thread y = new Thread(cThread);
				y.start();

				//TODO 
				// find a graceful way to shutdown.				
			}
		}		
	}
	
	private class RCV_Thread implements Runnable{	
		private volatile Socket sock;
		private volatile Scanner input;
		private volatile PrintWriter clientOut;
		private Boolean run = true;
		
		public RCV_Thread(Socket s){						
			try{
				//this.input = new Scanner(PrintWriter(tempSock));
				input = new Scanner(s.getInputStream());
				clientOut = new PrintWriter(s.getOutputStream());
				sock = s;
			} 
			catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run(){
			//System.out.println("[+] In \"ConnHandler\" private class \"RCV_Thread\" method \"run\"");
									
			while(run){
				String sender = "";
				
				if(input.hasNext()){
					String tempStr = input.nextLine();
					
					if(tempStr.contains("#!010101")){
						parseSysArg(tempStr);
					}
					else{					
						for(int i = 0; i < conArray.size(); i++){
							if(conArray.get(i).equals(sock)){
								sender = unameArray.get(i);
							}
						}
						for(int i = 0; i < conArray.size(); i++){							
							try{
								PrintWriter out = new PrintWriter(conArray.get(i).getOutputStream());
															
								out.print(sender+": ");
								out.println(tempStr);
								out.flush();
							} 
							catch (IOException e){
								e.printStackTrace();
							}
						}
					}
				}
			}
		}//end method run	
		
		private int parseSysArg(String str){
			System.out.println("[+] System request detected: "+str);
			
			if(str.contentEquals("#!010101users")){												
				for(int x = 0; x < unameArray.size(); x++){
					System.out.println("[+] Sending user array.");
					clientOut.println(unameArray.get(x));									
					clientOut.flush();
				}
				
				return 0;
			}
			else if(str.contentEquals("#!010101logout")){
				System.out.print("[+] Logging out user: ");
				
				for(int i = 0; i < conArray.size(); i++){
					if(conArray.get(i) == sock){
						System.out.println(unameArray.get(i));
						clientOut.println("[+] Logged Out.");
						clientOut.flush();
						conArray.remove(i);
						unameArray.remove(i);
						clientOut.close();
						run = false; //shuts the LST_Thread down
					}
				}
				
				return 0;
			}
			else if(str.contentEquals("#!010101")){
				//Placeholder
				return 0;
			}
			else if(str.contentEquals("#!010101")){
				//Placeholder
				return 0;
			}
			
			System.err.println("[!] Unable to parse system argument: "+str);
			return 1;
		}
	}
}//end class ConHandler

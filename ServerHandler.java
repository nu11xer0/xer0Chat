import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import Resources.Constants;

public class ConHandler{	
	private ServerSocket srvSock;
	private ArrayList<Socket> socketArray = new ArrayList<Socket>();
	private ArrayList<String> unameArray = new ArrayList<String>(); //not implemented yet...
	private LST_Thread lThread;
	private Thread x;

	public ConHandler() throws IOException{
		srvSock = new ServerSocket(Constants.DEFAULT_PORT);
		lThread = new LST_Thread();
		x = new Thread(lThread);
		x.start();
	}
	
	public ConHandler(int port) throws IOException{
		srvSock = new ServerSocket(port);
		lThread = new LST_Thread();
		x = new Thread(lThread);
		x.start();
	}

	public Boolean killSock() throws IOException{
		if(!srvSock.isClosed()){
			for(int i = 0; i < socketArray.size(); i++){
				socketArray.get(i).close();
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
			while(true){
				try{
					tempSock = srvSock.accept();
					socketArray.add(tempSock);

					PrintWriter out = new PrintWriter(tempSock.getOutputStream());
					in = new Scanner(tempSock.getInputStream());
					String uname = null;
					Boolean run = true;

					out.print("Enter a user name (10 char max): ");
					out.flush();

					while(uname == null && run){
						if(in.hasNext()){
							uname = in.nextLine();

							if(uname.length() > 10){
								unameArray.add(uname.substring(0,9));
							}
							else{
								unameArray.add(uname);
							}

							//send unameArray to all connected clients
							for(int i = 0; i < socketArray.size(); i++){
								if(!socketArray.get(i).isClosed()){
									try {
										PrintWriter outTemp = new PrintWriter(socketArray.get(i).getOutputStream());

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
									socketArray.remove(i);
									unameArray.remove(i);
								}
							}
						}
						if(uname == null) {
							out.print("output");
							if(out.checkError()) {
								System.out.println("[+] Client disconnected");
								tempSock.close();
								run = false;
								break;
							}
						}
					}
				}
				catch (IOException e){
					e.printStackTrace();
				}

				// Create and start the receive thread
				if(!tempSock.isClosed()) {
				RCV_Thread cThread = new RCV_Thread(tempSock);
				Thread y = new Thread(cThread);
				y.start();
				}				
			}
		}
	}

	private class RCV_Thread implements Runnable{
		private volatile Socket sock;
		private volatile Scanner input;
		private volatile PrintWriter clientOut;
		private volatile Boolean run = true;

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
			while(run){
				String sender = "";
				
				if(input.hasNext()){
					String tempStr = input.nextLine();

					if(tempStr.contains(Constants.SYS_INIT)){
						parseSysArg(tempStr);
					}
					else{
						for(int i = 0; i < socketArray.size(); i++){
							if(socketArray.get(i).equals(sock)){
								sender = unameArray.get(i);
							}
						}
						for(int i = 0; i < socketArray.size(); i++){
							try{
								PrintWriter out = new PrintWriter(socketArray.get(i).getOutputStream());

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
				
				if(sock.isClosed())
					run = false;
			}
		}//end method run

		private int parseSysArg(String str){
			//final String SYS_INIT = "#!010101";
			

			System.out.println("[+] System request detected: "+str);

			if(str.contentEquals(Constants.SYS_USERS)){
				for(int x = 0; x < unameArray.size(); x++){
					System.out.println("[+] Sending user array.");
					clientOut.println(unameArray.get(x));
					clientOut.flush();
				}

				return 0;
			}
			else if(str.contentEquals(Constants.SYS_LOGOUT)){
				System.out.print("[+] Logging out user: ");

				for(int i = 0; i < socketArray.size(); i++){
					if(socketArray.get(i) == sock){
						System.out.println(unameArray.get(i));
						clientOut.println("[+] Logged Out.");
						clientOut.flush();
						socketArray.remove(i);
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

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Server{
	private static ConHandler handler;

	public static void main(String[] args){
		int choice = 0;

		parseArgs(args);

		printMenu();

		while(true){
			Scanner consoleInput = new Scanner(System.in);

			try{
				choice = consoleInput.nextInt();
				consoleInput.nextLine(); //absorb the /n in the Scanner

				switch(choice){
					case 99: System.out.println("[+] Exiting...");
							 try{
								 if(handler.killSock()){
									 System.out.println("[+] Server shutdown successfully. Bye!");
								 }
								 else{
									 System.err.println("[!] Something went wrong while stopping the server.");
								 }
							 }
							 catch (IOException e){
								 e.printStackTrace(); 
							 }
							 consoleInput.close();
							 System.exit(0);
							 break;

					default: System.out.println("[+] Invalid input, try again.\n");
							 printMenu();
							 break;
				}
			}
			catch(InputMismatchException e){
				 System.out.println("[+] Ass sphincter says \"What\"?\n");
				 printMenu();
			}
		}
	}//end method main

	private static void parseArgs(String[] args){
		if(args.length > 0){
			Boolean next = false;
			
			for(String s: args){								
				if(next){					
					try{
						handler = new ConHandler(Integer.parseInt(s));
						System.out.println("Server port: "+s);
					}
					catch(Exception e){
						e.printStackTrace();
						System.exit(1);
					}					
				}
				else if(s.equals("-p")){
					next = true;
				}
			}
		}
		else{
			try{
				handler = new ConHandler();
				System.out.println("Server port: 4444");
			}
			catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		return;
	}

	public static void printMenu(){
		System.out.printf("%s%n%n%s%n%n%s",
						  "_-= xeroChat Server Console =-_",
						  "type 99 <enter> to quit.",
						  "> ");
		return;
	}//end method printMenu
}

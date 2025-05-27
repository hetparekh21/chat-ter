import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.util.Random;

class client{
	

	public static Socket soc = null;
	public static DataOutputStream out = null;
	public static DataInputStream in = null;
	public static listner thread;

	public static String[] animals = {"monkey","donkey","wolf","tiger","lion","kangaroo","zebra","owl","deer"};

	public static void main(String[] args){
		
		String name = null;
		
		if(args.length > 0){
			name = args[0];
		}else{
			Random random = new Random();
			name = "anonymous-" + animals[random.nextInt(animals.length)] + "-" + random.nextInt(1000) ;
		}

		System.out.println(name);

		try {

			// create socket and connect (address , port)
			soc = new Socket("192.168.29.2",8080);
			System.out.println("Connected to the server");
			out = new DataOutputStream(soc.getOutputStream());
			in = new DataInputStream(soc.getInputStream());
			thread = new listner(soc,out,in);
			thread.start();

			// send name first
			out.writeUTF(name);
				

		} catch (Exception e) {

			System.out.println("Unable to connect to the server");
			System.out.println(e);

		}
		
		
		//DataInputStream input = new DataInputStream(System.in);
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		// Scanner input = new Scanner(System.in);

		String line = "";

		while(!line.equals("/exit")){

			try {
				// System.out.print("You : ");
				line = input.readLine();
				
				if(line.equals("/cls")){
					clearConsole();
					continue;
				}

				out.writeUTF(line);

			} catch (Exception e) {

				System.out.println(e);

			}
	
		}

		try {
			thread.run = false;
			input.close();
			soc.close();

		} catch (Exception e) {
			System.out.println(e);
		}


	}

	public static void clearConsole(){

		try {
			String os = System.getProperty("os.name");

			if(os.contains("Windows")){
				new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
			}else{
				System.out.println("\033[H\033[2J");
				System.out.flush();
			}

		} catch (Exception e) {
			System.out.println("Something went wrong while clearing the console");
		}

	}


}

class listner extends Thread{

	private Socket soc;
	public DataOutputStream out;
	public DataInputStream in;
	public boolean run = true;

	public listner(Socket soc, DataOutputStream out, DataInputStream in){

		this.soc = soc;
		this.in = in;
		this.out = out;
		
	}	

	@Override
	public void run(){
		
		try {
			
			while(run){
				System.out.println(in.readUTF());
				// System.out.print("You : ");
			}

		} catch (Exception e) {
			//TODO: handle exception
		}

	}

}


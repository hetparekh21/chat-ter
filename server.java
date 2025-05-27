import java.net.*;
import java.io.*;
import java.util.*;

class server{

	// store users in rooms
	public static HashMap<String,HashSet<user>> rooms = new HashMap<>();
	// store passwords of rooms
	public static HashMap<String,String> passwords = new HashMap<>();

	public static int users = 0;

	public static void main(String[] args){

		rooms.put("general",new HashSet());
		rooms.put("private",new HashSet());

		try {
			// create server at 8080 port
			ServerSocket server = new ServerSocket(8080);
			System.out.println("server created");

			while(true){

				Socket soc = server.accept();

				System.out.println("New User Connected : " + ++users);

				// create user and start it's thread
				user u = new user(soc);
				u.start();

			}	

		} catch (Exception e) {

			System.out.println("Unable to start the server");
			
		}


	}

}

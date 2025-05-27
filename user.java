import java.net.*;
import java.util.*;
import java.io.*;

class user extends Thread{

	public user(Socket soc) throws Exception{

		try {

			this.soc = soc;
			this.in = new DataInputStream(soc.getInputStream());
			this.out = new DataOutputStream(soc.getOutputStream());
			myRooms = new HashSet<>();
			
		}catch (Exception e) {

			System.out.println("Unable to create user");
			throw new Exception("User not created");
		}
	}

	public String name;
	private Socket soc;
	public DataInputStream in;
	public DataOutputStream out;
	private String room = null;

	// keep track of self created rooms
	private Set<String> myRooms;

	@Override
	public void run(){

		try{

			this.name = in.readUTF();
			serverSays("type /info for any query about the enviornment");

			changeRoom();
			sysmsg(this.name + " has joined the room");

			String msg = "";

			while(true){
	
				msg = in.readUTF();
	
				if(msg.equals("/chrm")){
					changeRoom();
					serverSays("Room changed successfully to : " + this.room);
					sysmsg(this.name + " has joined the room");
					continue;
				}

				if(msg.equals("/mkrm")){
					String new_room = makeRoom();
					if(new_room == null){
						serverSays("Something went wrong while creating the room.");
					}else{
						changeRoom(new_room);
						serverSays("Room " + room + " created successfully.");
					}
					continue;
				}

				if(msg.equals("/mkrms")){
					String new_room = makeRoomSecure();
					if(new_room == null){
						serverSays("Something went wrong while creating the room.");
					}else{
						changeRoom(new_room);
						serverSays("Room " + room + " created successfully.");
					}
					continue;
				}

				if(msg.equals("/lrms")){
					serverSays("available rooms : " + getRooms());
					continue;
				}

				if(msg.equals("/info")){
					info();
					continue;
				}

				if(msg.equals("/mbrs")){

					StringBuilder members = new StringBuilder();

					for (user usr : server.rooms.get(room)) {
						members.append(usr.name + ", ");
					}

					serverSays(members.toString());
					continue;
				}
	
				if(msg.equals("/exit")){
					break;
				}

				broadcast(msg);

			}


			// close all the streams
			out.close();
			in.close();
			soc.close();

		} catch (Exception e) {

			System.out.println("Something went wrong in user thread");

		}finally{


			// remove self from the room
			if(server.rooms.get(room) != null){
				server.rooms.get(room).remove(this);
			}

			System.out.println("User disconnected : " + --server.users);


			// delete self created rooms if they are empty
			// for (String var : myRooms) {
			//	if(server.rooms.get(var) == null || server.rooms.get(var).size() == 0){
			//		server.rooms.remove(var);
			//		System.out.println("room " + var + " closed.");
			//	}
			// }

			// broadcast that user has left
			sysmsg(name + " has left the room.");

		}

	}

	private void info(){
		try {
			
			String info = 
				String.format(
				"""
				Available commands : 	/info 	: lists all the information you need.

							/lrms 	: lists all available rooms

							/mbrs 	: list all members in the current room

							/chrm 	: change your room

							/mkrm 	: make a new room

							/mkrms	: make a new room with password

							/cls 	: clears your console

							/exit 	: exits and closes the connection
		
				Name 		: %s 
				Available rooms	: %s 
				Current room 	: %s 
				Total members	: %s 
				"""
				,name ,getRooms() ,room ,getRoom().size());

			serverSays(info);

		} catch (Exception e) {
			System.out.println("Something went wrong in info.");	
		}
	}

	private static Set<String> getRooms(){
		return server.rooms.keySet();
	}

	private Set<user> getRoom(){
		return server.rooms.get(room);
	}


	public String makeRoomSecure(){
		
		try {
			
			serverSays("Enter Room name : ");
			String new_room;

			while(true){
			
				new_room = in.readUTF();
				if(server.rooms.get(new_room) == null){

					server.rooms.put(new_room, new HashSet());
					// get password
					serverSays("Enter Password : ");
					String password = in.readUTF();

					server.passwords.put(new_room,password);

					break;
				}else{
					serverSays("Room already exists. \nPlease pick another name");
				}

			}
			
			System.out.println("room " + new_room + " opened.");
			// System.out.println(server.rooms.keySet());
			// System.out.println(server.passwords);
			myRooms.add(new_room);
			return new_room;


		} catch (Exception e) {
			System.out.println("Something went wrong while making a room");
			return null;
		}

	}

	public void serverSays(String msg){
		try {
			out.writeUTF(Color.RED + msg + Color.RESET);
		} catch (Exception e) {
			System.out.println("Something Went wrong when server was saying : " + msg);
		}
	}

	public String makeRoom(){
		
		try {
			
			serverSays("Enter Room name : ");
			String new_room;

			while(true){
			
				new_room = in.readUTF();
				if(server.rooms.get(new_room) == null){
					server.rooms.put(new_room, new HashSet());
					break;
				}else{
					serverSays("Room already exists. \nPlease pick another name.");
				}

			}
			
			System.out.println("room " + new_room + " opened.");
			myRooms.add(new_room);
			return new_room;


		} catch (Exception e) {
			System.out.println("Something went wrong while making a room");
			return null;
		}

	}

	private void sysmsg(String msg){

		final String message = Color.CYAN + msg + Color.RESET;

		try {
			if(server.rooms.get(room) == null){
				return;
			}
			
			for(user u : server.rooms.get(room)){
	
				if(u.equals(this)){
					continue;
				}

				u.out.writeUTF(message);

			}
		} catch (Exception e) {
			
			System.out.println("Something Went wrong while sysmsg");
			System.out.println(server.rooms);

		}	

	}

	private void broadcast(String msg){
		
		final String message = Color.BLUE + this.name + " : " + msg + Color.RESET;

		try {
			
			for(user u : server.rooms.get(room)){
	
				if(u.equals(this)){
					continue;
				}

				u.out.writeUTF(message);

			}
		} catch (Exception e) {
			
			System.out.println("Something Went wrong while broadcasting");

		}	

	}

	public void changeRoom(String choice){

		Set<String> rooms = getRooms();
		
		// remove itself from current room
		if(server.rooms.get(room) != null){
			server.rooms.get(room).remove(this);
		}

		// broadcast that the user has left the room
		sysmsg(this.name + " has left the room.");
				
		// update current room name
		this.room = choice;
		// add itself in the new room
		server.rooms.get(room).add(this);

	}

	public void changeRoom(){
	
		Set<String> rooms = getRooms();

		while(true){

			try {

				String menu = "Available rooms : " + rooms;
				serverSays(menu);

				String choice = in.readUTF();
				if(rooms.contains(choice)){
				
					// remove itself from current room
					if(server.rooms.get(room) != null){
						server.rooms.get(room).remove(this);
					}

					String room_password;

					if( (room_password = server.passwords.get(choice)) != null ){
						serverSays("Enter Password : ");
						String pass = in.readUTF();
						if (!room_password.equals(pass)) {
							serverSays("Auth Failed!!!");
						}else{
							// indicates ahead that auth is done or does not exist
							room_password = null;
						}
					}

					if (room_password == null) {
							
						// broadcast that the user has left the room
						sysmsg(this.name + " has left the room");
				
						// update current room name
						this.room = choice;
						// add itself in the new room
						server.rooms.get(room).add(this);
			
						break;
					}

				}else{
					serverSays("Please Select a valid room");
				}


			} catch (Exception e) {

				System.out.println("Something went wrong while changing room");
				break;
			}

		}

	}

}

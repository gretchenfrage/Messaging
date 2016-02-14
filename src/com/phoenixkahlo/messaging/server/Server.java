package com.phoenixkahlo.messaging.server;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.phoenixkahlo.messaging.server.commands.Nickname;

/*
 * Represents a complete, runnable messaging server
 */
public class Server {

	public static final int PORT = 14789;
	
	public static final boolean PRINT_DEBUG = false;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	private MessageRepository repository;
	private Waiter waiter;
	private HeartBeat heartBeat;
	private ServerFrame frame;
	
	private CommandExecuter commandExecuter;
	private Nickname nickname;

	public Server() {
		repository = new MessageRepository();
		waiter = new Waiter(new MessagingConnectionFactory(this), PORT);
		heartBeat = new HeartBeat(this);
		frame = new ServerFrame();
		
		commandExecuter = new CommandExecuter();
		nickname = new Nickname(this);
		commandExecuter.addCommand("nickname", nickname);
	}

	public void start() {
		for (String s : repository.getAllMessages()) {
			System.out.println(s);
			frame.println(s);
		}
		waiter.start();
		heartBeat.start();
		frame.start();
		System.out.println("~~~ MESSAGING SERVER STARTED~~~");
	}

	private List<MessagingConnection> connections = new ArrayList<MessagingConnection>();

	public void addConnection(MessagingConnection connection) {
		connections.add(connection);
		for (String s : repository.getAllMessages()) {
			connection.sendMessage(s);
		}
		connection.start();
		if (nickname.hasNickname(connection.toString()))
			createMessage("(" + connection + ") " + nickname.nicknameOf(connection.toString()) + " connected");
		else
			createMessage(connection + " connected");
	}
	
	public void removeConnection(MessagingConnection connection) {
		synchronized (connection) {
			connections.remove(connection);
			connection.terminate();
		}
		if (nickname.hasNickname(connection.toString()))
			createMessage("(" + connection + ") " + nickname.nicknameOf(connection.toString()) + " disconnected");
		else
			createMessage(connection + " disconnected");
	}

	/*
	 * Sends message to all connected clients
	 */
	public void sendMessage(String message) {
		for (int i = connections.size() - 1; i >= 0; i--) {
			connections.get(i).sendMessage(message);
		}
	}
	
	public void sendTo(String targetIP, String message) {
		for (int i = connections.size() - 1; i >= 0; i--) {
			if (connections.get(i).toString().equals(targetIP))
				connections.get(i).sendMessage(message);
		}
	}

	/*
	 * Called upon by MessagingConnection objects in their threads
	 * Checks for and executes commands, or prints it, displays it, saves it to the repository, and sends it
	 * Recieves exactly what the sender sent, nothing appended or prepended
	 */
	public void recieveMessage(String sender, String message) {
		if (message.toCharArray()[0] == '/') {
			commandExecuter.execute(sender, message);
		} else {
			if (nickname.hasNickname(sender))
				createMessage("(" + sender + ") " + nickname.nicknameOf(sender) + " > " + message);
			else 
				createMessage(nickname.nicknameOf(sender) + " > " + message);
		}
	}
	
	/*
	 * Not to be called upon by MessagingConnection objects/regular user chat
	 * Does not check for or execute commands
	 * Takes what is inputted, gives it a timestamp, prints it, displays it, saves it to the repository, and sends it
	 */
	public void createMessage(String message) {
		Calendar calendar = Calendar.getInstance();
		String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
		String minute = Integer.toString(calendar.get(Calendar.MINUTE));
		String second = Integer.toString(calendar.get(Calendar.SECOND));
		if (hour.length() < 2) hour = "0" + hour;
		if (minute.length() < 2) minute = "0" + minute;
		if (second.length() < 2) second = "0" + second;
		message = "[" + hour + ":" + minute + ":" + second + "] " + message;
		System.out.println(message);
		frame.println(message);
		repository.addMessage(message);
		sendMessage(message);
	}

}

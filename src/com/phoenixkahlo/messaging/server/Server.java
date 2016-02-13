package com.phoenixkahlo.messaging.server;
import java.util.List;
import java.util.ArrayList;

/*
 * Represents a complete, runnable messaging server
 */
public class Server {

	public static final int PORT = 39422;
	
	public static final boolean PRINT_DEBUG = false;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	private MessageRepository repository;
	private Waiter waiter;
	private HeartBeat heartBeat;
	private ServerFrame frame;

	public Server() {
		repository = new MessageRepository();
		waiter = new Waiter(new MessagingConnectionFactory(this), PORT);
		heartBeat = new HeartBeat(this);
		frame = new ServerFrame();
	}

	public void start() {
		for (String s : repository.getAllMessages()) {
			System.out.println(s);
		}
		waiter.start();
		heartBeat.start();
		frame.start();
		System.out.println("~~~ MESSAGING SERVER STARTED~~~");
	}

	private List<MessagingConnection> connections = new ArrayList<MessagingConnection>();

	public void addConnection(MessagingConnection connection) {
		System.out.println("Client connected: " + connection);
		connections.add(connection);
		for (String s : repository.getAllMessages()) {
			connection.sendMessage(s);
		}
		connection.start();
	}
	
	public void removeConnection(MessagingConnection connection) {
		System.out.println("Client disconnected: " + connection);
		synchronized (connection) {
			connections.remove(connection);
			connection.terminate();
		}
	}

	/*
	 * Sends message to all connected clients
	 */
	public void sendMessage(String message) {
		for (int i = connections.size() - 1; i >= 0; i--) {
			connections.get(i).sendMessage(message);
		}
	}

	/*
	 * Called upon by MessagingConnection threads
	 */
	public void recieveMessage(String message) {
		System.out.println(message);
		frame.println(message);
		repository.addMessage(message);
		sendMessage(message);
	}

}

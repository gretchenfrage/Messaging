package com.phoenixkahlo.messaging.server;
import java.util.ArrayList;
import java.util.List;

import com.phoenixkahlo.messaging.messagetypes.Message;
import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.utils.Waiter;

/*
 * Represents a complete, runnable messaging server
 */
public class Server {

	public static final int DEFAULT_PORT = 39424;
	
	public static final boolean PRINT_DEBUG = false;
	
	public static void main(String[] args) {
		if (args.length == 1) {
			Server server = new Server(Integer.parseInt(args[0]));
			server.start();
		} else {
			Server server = new Server(DEFAULT_PORT);
			server.start();
		}
	}

	private MessageRepository repository;
	private Waiter waiter;
	private HeartBeatSender heartBeat;
	private ServerFrame frame;

	public Server(int port) {
		repository = new MessageRepository();
		waiter = new Waiter(new MessagingConnectionFactory(this), port);
		heartBeat = new HeartBeatSender(this);
		frame = new ServerFrame();
	}

	public void start() {
		for (MessageOld m : repository.getAllMessages()) {
			System.out.println(m);
			frame.addMessage(m);
		}
		waiter.start();
		heartBeat.start();
		frame.start();
		System.out.println("~~~ MESSAGING SERVER STARTED~~~");
	}

	private List<MessagingConnection> connections = new ArrayList<MessagingConnection>();

	/*
	 * Called upon by waiter thread when new connections are accepted
	 */
	public void addConnection(MessagingConnection connection) {
		connections.add(connection);
		for (MessageOld m : repository.getAllMessages()) {
			connection.send(m);
		}
		connection.start();
		//TODO: implement joined the chat message
	}
	
	/*
	 * Called upon by the connections themselves, after recieving an IOException as a result of disconnection
	 */
	public void removeConnection(MessagingConnection connection) {
		synchronized (connection) {
			connections.remove(connection);
			connection.terminate();
		}
		//TODO: implement left the chat message
	}

	/*
	 * Sends message to all connected clients
	 */
	public void send(Sendable sendable) {
		for (int i = connections.size() - 1; i >= 0; i--) {
			connections.get(i).send(sendable);
		}
	}
	
	/*
	 * Called upon by MessagingConnection threads after recieving message from client
	 */
	public void recieveMessage(Message message) {
		frame.addComponent(message.toComponent());
		repository.addMessage(message);
		send(message);
	}
	/*
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
	*/

}

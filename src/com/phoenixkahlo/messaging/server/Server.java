package com.phoenixkahlo.messaging.server;
import java.util.ArrayList;
import java.util.List;

import com.phoenixkahlo.messaging.messagetypes.DisplayClientFrameCommand;
import com.phoenixkahlo.messaging.messagetypes.Message;
import com.phoenixkahlo.messaging.messagetypes.RawTextMessage;
import com.phoenixkahlo.messaging.messagetypes.RelaunchClientCommand;
import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;
import com.phoenixkahlo.messaging.utils.Waiter;

/*
 * Represents a complete, runnable messaging server
 */
public class Server {

	public static final int DEFAULT_PORT = 39422;
	
	public static void main(String[] args) {
		if (args.length == 1) {
			Server server = new Server(Integer.parseInt(args[0]));
			server.start();
		} else {
			Server server = new Server(DEFAULT_PORT);
			server.start();
		}
	}

	private SendableCoder coder;
	private MessageRepository repository;
	private Waiter waiter;
	private HeartBeat heartBeat;
	private ServerFrame frame;
	
	public Server(int port) {
		coder = new SendableCoder();
		repository = new MessageRepository(coder);
		waiter = new Waiter(new MessagingConnectionFactory(this, coder), port);
		heartBeat = new HeartBeat(this);
		frame = new ServerFrame();
	}

	public void start() {
		for (Message m : repository.getAllMessages()) {
			System.out.println(m);
			frame.addComponent(m.toComponent());
		}
		frame.scrollToBottom();
		waiter.start();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				waiter.terminate(); // Make sure the relaunched clients don't reconnect to this server before finished closing
				send(new RelaunchClientCommand());
			}
		}, "Shutdown hook thread"));
		heartBeat.start();
		frame.start();
		System.out.println("~~~ MESSAGING SERVER STARTED~~~");
	}

	private List<MessagingConnection> connections = new ArrayList<MessagingConnection>();

	/*
	 * Called upon by the connections themselves, after receiving ConnectionActivator
	 */
	public void activateConnection(MessagingConnection connection) {
		connections.add(connection);
		for (Message m : repository.getAllMessages()) {
			connection.send(m);
		}
		connection.send(new DisplayClientFrameCommand());
		recieveMessage(new RawTextMessage(connection.getNickname() + " joined the chat"));
	}
	
	/*
	 * Called upon by the connections themselves, after receiving a ConnectionDeactivator or IOException
	 */
	public void deactivateConnection(MessagingConnection connection) {
		synchronized (connection) {
			connections.remove(connection);
			connection.terminate();
		}
		recieveMessage(new RawTextMessage(connection.getNickname() + " left the chat"));
	}

	/*
	 * Sends message to all connected clients
	 */
	public void send(Sendable sendable) {
		for (int i = connections.size() - 1; i >= 0; i--) {
			// Iterate backwards because MessagingConnection.send may lead program flow to Server.removeConnection
			connections.get(i).send(sendable);
		}
	}
	
	/*
	 * Called upon by MessagingConnection threads after receiving message from client
	 */
	public void recieveMessage(Message message) {
		System.out.println(message);
		frame.addComponent(message.toComponent());
		repository.addMessage(message);
		send(message);
	}

}

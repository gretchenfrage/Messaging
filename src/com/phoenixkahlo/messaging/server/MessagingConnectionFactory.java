package com.phoenixkahlo.messaging.server;
import java.net.Socket;
import java.net.SocketException;

import com.phoenixkahlo.messaging.utils.ConnectionFactory;

/*
 * A ConnectionFactory that produces MessagingConnections to represent client connections
 */
public class MessagingConnectionFactory implements ConnectionFactory {

	private Server server;

	public MessagingConnectionFactory(Server server) {
		this.server = server;
	}

	@Override
	public void createConnection(Socket socket) {
		try {
			socket.setSoTimeout(7_000);
		} catch (SocketException e) {
			System.err.println("SocketException in setting timeout");
			e.printStackTrace();
		}
		server.addConnection(new MessagingConnection(server, socket));
	}

}
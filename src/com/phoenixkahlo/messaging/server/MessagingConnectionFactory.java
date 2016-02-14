package com.phoenixkahlo.messaging.server;
import java.net.Socket;

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
		server.addConnection(new MessagingConnection(server, socket));
	}

}
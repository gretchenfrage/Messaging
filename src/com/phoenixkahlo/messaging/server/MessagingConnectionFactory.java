package com.phoenixkahlo.messaging.server;
import java.net.Socket;

import com.phoenixkahlo.messaging.messagetypes.SendableCoder;
import com.phoenixkahlo.messaging.utils.ConnectionFactory;

/*
 * A ConnectionFactory that produces MessagingConnections to represent client connections
 */
public class MessagingConnectionFactory implements ConnectionFactory {

	private Server server;
	private SendableCoder coder;

	public MessagingConnectionFactory(Server server, SendableCoder coder) {
		this.server = server;
		this.coder = coder;
	}

	@Override
	public void createConnection(Socket socket) {
		MessagingConnection connection = new MessagingConnection(server, socket, coder);
		connection.start();
	}

}
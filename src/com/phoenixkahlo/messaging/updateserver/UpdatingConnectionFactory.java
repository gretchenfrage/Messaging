package com.phoenixkahlo.messaging.updateserver;

import java.net.Socket;

import com.phoenixkahlo.messaging.utils.ConnectionFactory;

public class UpdatingConnectionFactory implements ConnectionFactory {

	private UpdateServer server;
	
	public UpdatingConnectionFactory(UpdateServer server) {
		this.server = server;
	}
	
	@Override
	public void createConnection(Socket socket) {
		UpdatingConnection connection = new UpdatingConnection(socket);
		server.addConnection(connection);
		connection.start();
	}
	
}

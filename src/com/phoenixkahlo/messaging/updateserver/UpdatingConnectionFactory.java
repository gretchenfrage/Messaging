package com.phoenixkahlo.messaging.updateserver;

import java.net.Socket;

import com.phoenixkahlo.messaging.utils.ConnectionFactory;

public class UpdatingConnectionFactory implements ConnectionFactory {

	@Override
	public void createConnection(Socket socket) {
		UpdatingConnection connection = new UpdatingConnection(socket);
		connection.start();
	}
	
}

package com.phoenixkahlo.messaging.updateserver;

import java.net.Socket;

import com.phoenixkahlo.messaging.utils.ConnectionFactory;

public class UpdatingConnectionFactory implements ConnectionFactory {

	private UpdatingFileCache fileCache;
	
	public UpdatingConnectionFactory(UpdatingFileCache fileCache) {
		this.fileCache = fileCache;
	}
	
	@Override
	public void createConnection(Socket socket) {
		UpdatingConnection connection = new UpdatingConnection(socket, fileCache);
		connection.start();
		System.out.println("Client connected");
	}
	
}

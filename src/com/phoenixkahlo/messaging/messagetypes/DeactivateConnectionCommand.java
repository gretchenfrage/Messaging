package com.phoenixkahlo.messaging.messagetypes;

import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;

/*
 * Sent by the client to get the server to deactivate it's connection
 * Used to avoid the latency of socket disconnection detection
 */
public class DeactivateConnectionCommand implements Sendable {

	public DeactivateConnectionCommand() {}
	
	public DeactivateConnectionCommand(InputStream in) {}
	
	@Override
	public void write(OutputStream out) {}
	
	@Override
	public void effectClient(Client client) {
		System.err.println("ConnectionDeactivator received by client");
	}
	
	@Override
	public void effectServer(MessagingConnection connection) {
		connection.getServer().deactivateConnection(connection);
	}
	
}

package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;

/*
 * Sent by the client to get the server to activate the connection
 * Contains the nickname the connection will start with, or empty string if none
 * Exists to contain activate data (nickname for "joined the chat" message)
 */
public class ActivateConnectionCommand implements Sendable {
	
	public ActivateConnectionCommand() {}
	
	public ActivateConnectionCommand(InputStream in) throws IOException {}

	@Override
	public void write(OutputStream out) {}

	@Override
	public void effectClient(Client client) {
		System.err.println("ConnectionActivator received by client");
	}

	@Override
	public void effectServer(MessagingConnection connection) {
		connection.getServer().activateConnection(connection);
	}
	
}

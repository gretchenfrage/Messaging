package com.phoenixkahlo.messaging.messagetypes;

import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;

public class RelaunchClientCommand implements Sendable {

	public RelaunchClientCommand() {}
	
	public RelaunchClientCommand(InputStream in) {}
	
	@Override
	public void write(OutputStream out) {}
	
	@Override
	public void effectClient(Client client) {
		Client.relaunch();
	}
	
	@Override
	public void effectServer(MessagingConnection connection) {
		System.err.println("RelaunchClientCommand received by server");
	}
	
}

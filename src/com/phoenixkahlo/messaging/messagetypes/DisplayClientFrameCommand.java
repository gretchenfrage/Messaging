package com.phoenixkahlo.messaging.messagetypes;

import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;

/*
 * Sent by servers the clients once finished sending already existing messages
 */
public class DisplayClientFrameCommand implements Sendable {

	public DisplayClientFrameCommand() {}
	
	public DisplayClientFrameCommand(InputStream in) {}
	
	@Override
	public void write(OutputStream out) {}

	@Override
	public void effectClient(Client client) {
		client.getFrame().start();
	}

	@Override
	public void effectServer(MessagingConnection connection) {
		System.err.println("DisplayClientFrameCommand received by server");
	}

}

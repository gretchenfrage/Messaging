package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;

public class Pulse implements Sendable {

	public static final Pulse INSTANCE = new Pulse();
	
	private Pulse() {}
	
	public static Pulse construct(InputStream in) {
		return INSTANCE;
	}
	
	@Override
	public void write(OutputStream out) throws IOException {}

	@Override
	public void effectClient(Client client) {}

	@Override
	public void effectServer(MessagingConnection connection) {}
	
	@Override
	public String toString() {
		return "pulse";
	}

}

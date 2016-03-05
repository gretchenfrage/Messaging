package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;

public interface Sendable {

	/*
	 * Does not encode header, and thus should only ever be called by SendableCoder
	 */
	void write(OutputStream out) throws IOException;
	
	void effectClient(Client client);
	
	void effectServer(MessagingConnection connection);
	
	/*
	 * Sendables that are registered in SendableCoder must have
	 * public Sendable(InputStream in) throws IOException
	 * or
	 * public static Sendable construct(InputStream in) throws IOException
	 */
	
}

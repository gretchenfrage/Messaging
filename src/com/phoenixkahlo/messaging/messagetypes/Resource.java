package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Represents a resource containing sizable amounts of data that will be cached on the hard drive on both
 * the client and server sides and sent through the Internet on an as-needed basis.
 * Subclasses are responsible for reading and writing actual data.
 */
public abstract class Resource implements Sendable {
	
	/*
	 * IDs should not include file extensions, that should be handled by the ResourceRepository
	 */
	private String resourceID;
	
	public Resource() {
		resourceID = createResourceID();
	}
	
	public Resource(InputStream in) throws IOException {
		resourceID = Protocol.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		Protocol.writeString(resourceID, out);
	}
	
	@Override
	public void effectClient(Client client) {
		//TODO: implement
	}
	
	@Override
	public void effectServer(MessagingConnection connection) {
		//TODO: implement
	}
	
	public String getResourceID() {
		return resourceID;
	}
	
	public static String createResourceID() {
		StringBuilder out = new StringBuilder();
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_".toCharArray();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			out.append(chars[random.nextInt(chars.length)]);
		}
		return out.toString();
	}
	
}

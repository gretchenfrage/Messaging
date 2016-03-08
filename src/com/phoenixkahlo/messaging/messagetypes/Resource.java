package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Represents a resource that may be stored in a ResourceRepository
 */
public abstract class Resource implements Sendable {
	
	/*
	 * Is left blank when created by client, to be assigned when reaching the server
	 */
	private String resourceID;
	private byte[] data;
	
	public Resource(byte[] data) {
		resourceID = getResourceID() + "." + getExtension();
		this.data = data;
	}
	
	public Resource(InputStream in) throws IOException {
		resourceID = Protocol.readString(in);
		data = Protocol.readByteArray(in);
	}
	
	/*
	 * Does not return extension
	 */
	public static String createResourceID() {
		StringBuilder out = new StringBuilder();
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_".toCharArray();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			out.append(chars[random.nextInt(chars.length)]);
		}
		return out.toString();
	}
	
	public abstract String getExtension();
	
	@Override
	public void write(OutputStream out) throws IOException {
		Protocol.writeString(resourceID, out);
		Protocol.writeByteArray(data, out);
	}
	
	@Override
	public void effectClient(Client client) {
		client.getResourceRepository().addResource(this);
	}
	
	@Override
	public void effectServer(MessagingConnection connection) {
		connection.getServer().getResourceRepository().addResource(this);
	}
	
	public String getResourceID() {
		return resourceID;
	}
	
	public byte[] getData() {
		return data;
	}
	
}

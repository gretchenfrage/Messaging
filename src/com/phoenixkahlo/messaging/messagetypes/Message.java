package com.phoenixkahlo.messaging.messagetypes;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.Protocol;

public abstract class Message implements Sendable {

	private String name;
	private String ip = ""; // Is reset when recieved by server
	
	/*
	 * Called upon when first constructed in client end
	 */
	public Message(String name) {
		this.name = name;
	}
	
	public Message(InputStream in) throws IOException {
		name = Protocol.readString(in);
		ip = Protocol.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		out.write(getHeader());
		Protocol.writeString(name, out);
		Protocol.writeString(ip, out);
		writeContents(out);
	}
	
	public abstract int getHeader();
	
	public abstract void writeContents(OutputStream out) throws IOException;

	@Override
	public void effectClient(Client client) {
		client.recieveMessage(this);
	}

	@Override
	public void effectServer(MessagingConnection connection) {
		ip = connection.getIP();
		connection.getServer().recieveMessage(this);
	}
	
	public String getName() {
		return name;
	}
	
	public abstract Component toComponent();

}

package com.phoenixkahlo.messaging.messagetypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.Protocol;

public abstract class SourcedMessage extends Message {

	private String name;
	private String ip = ""; // Is reset when reaches server
	
	public SourcedMessage(String name) {
		this.name = name;
	}
	
	public SourcedMessage(InputStream in) throws IOException {
		super(in); // Read timestamp
		name = Protocol.readString(in);
		ip = Protocol.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		super.write(out); // Write timestamp
		Protocol.writeString(name, out);
		Protocol.writeString(ip, out);
	}
	
	@Override
	public void effectServer(MessagingConnection connection) {
		// Must reset ip when reaching server
		ip = connection.getIP();
		super.effectServer(connection); // Make recieved
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(" + ip + ")" + name + ": ";
	}
	
}

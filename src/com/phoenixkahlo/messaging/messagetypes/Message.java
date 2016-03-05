package com.phoenixkahlo.messaging.messagetypes;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import com.phoenixkahlo.messaging.client.Client;
import com.phoenixkahlo.messaging.server.MessagingConnection;
import com.phoenixkahlo.messaging.utils.Protocol;

/*
 * Represents sendables that can be displayed in the chat and have a timestamp
 * Represents messages that come from a particular client and have a name, ip, and timestamp
 */
public abstract class Message implements Sendable {

	private String timestamp;
	
	/*
	 * When first constucted, sets time to current time
	 */
	public Message() {
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CST"));
		timestamp = format.format(calendar.getTime());
	}
	
	
	public Message(InputStream in) throws IOException {
		timestamp = Protocol.readString(in);
	}
	
	/*
	 * Should always be overridden
	 * Although it would not actually break the app if it wasn't
	 */
	@Override
	public void write(OutputStream out) throws IOException {
		Protocol.writeString(timestamp, out);
	}
		
	@Override
	public void effectClient(Client client) {
		client.recieveMessage(this);
	}

	@Override
	public void effectServer(MessagingConnection connection) {
		connection.getServer().recieveMessage(this);
	}
	
	public abstract Component toComponent();
	
	@Override
	public String toString() {
		return "[" + timestamp + "]";
	}
	
}

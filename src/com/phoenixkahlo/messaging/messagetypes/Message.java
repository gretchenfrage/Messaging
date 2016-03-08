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
 * Represents Sendables that can be displayed in the chat and have a timestamp
 * Represents messages that come from a particular client and have a name, ip, and timestamp
 * Any subclass of message should be backwards compatible with the data stream version of itself,
 * as they will be stored long term on the server side
 */
public abstract class Message implements Sendable {

	private String timestamp;
	
	/*
	 * When first constructed, sets time to current time
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
	 * Should always be overridden and immediately called with super.write(out)
	 * Although it would not actually break the app if it weren't
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

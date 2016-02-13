package com.phoenixkahlo.messaging.server;
import java.util.ArrayList;
import java.util.List;

/*
 * Stores messages and saves them in a file system
 */
public class MessageRepository {

	private List<String> messages = new ArrayList<String>();

	public void addMessage(String message) {
		messages.add(message);
	}
	
	public String[] getAllMessages() {
		synchronized (messages) {
			String[] out = new String[messages.size()];
			for (int i = 0; i < out.length; i++) {
				out[i] = messages.get(i);
			}
			return out;
		}
	}

}

package com.phoenixkahlo.messaging.server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.MessagingProtocol;

/*
 * Represents a connection to a client. Waits on messages in its own thread, sends
 * messages on threads of other MessagingConnections that have received messages
 */
public class MessagingConnection extends Thread {

	private Server server;
	private Socket socket;
	private volatile boolean shouldContinueRunning = true;
	
	public MessagingConnection(Server server, Socket socket) {
		super("Recieving thread for " + socket.getInetAddress());
		this.server = server;
		this.socket = socket;
	}
	
	public void terminate() {
		shouldContinueRunning = false;
	}

	@Override
	public void run() {
		if (Server.PRINT_DEBUG)
			System.out.println("MessagingConnection thread started: " + this);
		try {
			InputStream in = socket.getInputStream();
			while (shouldContinueRunning) {
				String message = MessagingProtocol.readMessage(in);
				// Sends exactly what the sender sent, nothing appended or prepended
				if (message.length() > 0) server.recieveMessage(toString(), message);
			}
		} catch (IOException e) {
			if (Server.PRINT_DEBUG)
				System.out.println("Disconnected socket on connection: " + this);
			server.removeConnection(this);
		}
	}
	
	public void sendMessage(String message) {
		try {
			OutputStream out = socket.getOutputStream();
			MessagingProtocol.writeMessage(out, message);
		} catch (IOException e) {
			if (Server.PRINT_DEBUG)
				System.out.println("Disconnected socket on connection: " + this);
			server.removeConnection(this);
		}
	}
	
	@Override
	public String toString() {
		return socket.getInetAddress().toString();
	}

}

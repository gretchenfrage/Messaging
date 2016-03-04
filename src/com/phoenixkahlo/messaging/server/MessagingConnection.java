package com.phoenixkahlo.messaging.server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;

/*
 * Represents a connection to a client. Waits on messages in its own thread, sends
 * messages on threads of other MessagingConnections that have received messages
 */
public class MessagingConnection extends Thread {

	private Server server;
	private Socket socket;
	private String senderName;
	private volatile boolean shouldContinueRunning = true;
	
	public MessagingConnection(Server server, Socket socket) {
		super("Recieving thread for " + socket.getInetAddress());
		this.server = server;
		this.socket = socket;
		senderName = socket.getInetAddress().toString();
	}
	
	public void terminate() {
		shouldContinueRunning = false;
	}

	@Override
	public void run() {
		System.out.println("MessagingConnection thread started: " + getIP());
		try {
			InputStream in = socket.getInputStream();
			while (shouldContinueRunning) {
				// It is trusted that the client will never send a heartbeat
				// The client has no reason to include a sender as it is overridden when recieving
				MessageOld recieved = SendableCoder.readMessage(in);
				recieved.setSender(senderName);
				server.recieveMessage(recieved, getIP());
			}
		} catch (IOException e) {
			System.out.println("Disconnected socket on connection: " + getIP());
			server.removeConnection(this);
		}
	}
	
	public void send(Sendable sendable) {
		try {
			OutputStream out = socket.getOutputStream();
			sendable.write(out);
		} catch (IOException e) {
			System.out.println("Disconnected socket on connection: " + getIP());
			server.removeConnection(this);
		}
	}
	
	public String getIP() {
		return socket.getInetAddress().toString();
	}
	
	public Server getServer() {
		return server;
	}

}

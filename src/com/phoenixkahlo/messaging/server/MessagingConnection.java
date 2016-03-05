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
	private SendableCoder coder;
	private volatile boolean shouldContinueRunning = true;
	
	public MessagingConnection(Server server, Socket socket, SendableCoder coder) {
		super("Recieving thread for " + socket.getInetAddress());
		this.server = server;
		this.socket = socket;
		this.coder = coder;
	}
	
	public void terminate() {
		shouldContinueRunning = false;
		interrupt();
	}

	@Override
	public void run() {
		System.out.println("MessagingConnection thread started: " + getIP());
		try {
			InputStream in = socket.getInputStream();
			while (shouldContinueRunning) {
				Sendable recieved = coder.read(in);
				recieved.effectServer(this);
			}
		} catch (IOException e) {
			System.out.println("Disconnected socket on connection: " + getIP());
			server.removeConnection(this);
		}
	}
	
	public void send(Sendable sendable) {
		try {
			OutputStream out = socket.getOutputStream();
			coder.write(sendable, out);
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

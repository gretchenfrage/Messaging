package com.phoenixkahlo.messaging.client;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.messagetypes.Sendable;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;

/*
 * Listens to a server for incoming messages
 */
public class ClientListener extends Thread {

	private SendableCoder coder;
	private Client client;
	private Socket socket;
	
	public ClientListener(Client client, Socket socket, SendableCoder coder) {
		this.coder = coder;
		this.client = client;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("Launching ClientListener thread");
		try {
			InputStream in = socket.getInputStream();
			while (true) {
				Sendable received = coder.read(in);
				received.effectClient(client);
			}
		} catch (IOException e) {
			Client.relaunch();
		}
	}
	
}

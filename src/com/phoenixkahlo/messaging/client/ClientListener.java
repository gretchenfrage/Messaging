package com.phoenixkahlo.messaging.client;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.phoenixkahlo.messaging.messagetypes.MessageOld;
import com.phoenixkahlo.messaging.messagetypes.SendableCoder;

/*
 * Listens to a server for incoming messages
 */
public class ClientListener extends Thread {

	private Client client;
	private Socket socket;
	
	public ClientListener(Client client, Socket socket) {
		this.client = client;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("Launching ClientListener thread");
		try {
			InputStream in = socket.getInputStream();
			while (true) {
				MessageOld message = SendableCoder.readMessage(in);
				if (message != null) client.recieveMessage(message);
			}
		} catch (IOException e) {
			Client.relaunch("Server disconnected", e);
		}
	}
	
}
